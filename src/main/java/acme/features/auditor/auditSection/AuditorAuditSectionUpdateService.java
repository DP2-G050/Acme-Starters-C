
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditSection;
import acme.entities.audits.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionUpdateService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditSection.getReport() != null && //
			this.auditSection.getReport().getAuditor().isPrincipal() && //
			this.auditSection.getReport().isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);

		Integer totalHours = this.auditSection.getReport().getHours();
		totalHours = totalHours == null ? 0 : totalHours;

		Integer newHours = this.auditSection.getHours();

		if (newHours != null) {
			boolean validHours = totalHours + newHours <= 1000;
			super.state(validHours, "hours", "acme.validation.auditSection.sumHours");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("auditReportId", this.auditSection.getReport().getId());
		tuple.put("draftMode", this.auditSection.getReport().isDraftMode());
		tuple.put("kinds", choices);
	}

}
