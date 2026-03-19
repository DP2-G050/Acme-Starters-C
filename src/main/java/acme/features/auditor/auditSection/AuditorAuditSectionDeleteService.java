
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
public class AuditorAuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.auditSection != null && this.auditSection.getReport().getAuditor().isPrincipal() && this.auditSection.getReport().isDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {

		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.repository.delete(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());
		Tuple tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");

		tuple.put("auditReportId", this.auditSection.getReport().getId());
		tuple.put("draftMode", this.auditSection.getReport().isDraftMode());
		tuple.put("kinds", choices);
	}
}
