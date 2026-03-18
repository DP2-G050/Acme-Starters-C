
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.entities.audits.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;


	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		AuditReport auditReport = this.repository.findAuditReportById(auditReportId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setReport(auditReport);
	}

	@Override
	public void authorise() {
		boolean status = this.auditSection.getReport() != null && this.auditSection.getReport().getAuditor().isPrincipal() && this.auditSection.getReport().isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);

		// Validación de horas acumuladas solo si no es null
		Integer newHours = this.auditSection.getHours();
		if (newHours != null) {
			Integer currentHours = this.auditSection.getReport().getHours();
			boolean validHours = currentHours + newHours <= 1000;
			super.state(validHours, "hours", "acme.validation.AuditSection.sumPercentages");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());
		Tuple tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");

		tuple.put("auditReportId", super.getRequest().getData("auditReportId", int.class));
		tuple.put("draftMode", this.auditSection.getReport().isDraftMode());
		tuple.put("kinds", choices);
	}
}
