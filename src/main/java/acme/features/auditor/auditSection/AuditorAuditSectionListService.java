
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditReport						auditReport;
	private Collection<AuditSection>		auditSections;


	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);

		this.auditReport = this.repository.findAuditReportById(auditReportId);
		this.auditSections = this.repository.findAuditSectionsByAuditReportId(auditReportId);
	}

	@Override
	public void authorise() {
		boolean status;

		int principalId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = this.auditReport != null && (this.auditReport.getAuditor().getId() == principalId || !this.auditReport.isDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");

		int principalId = super.getRequest().getPrincipal().getActiveRealm().getId();

		showCreate = this.auditReport.isDraftMode() && this.auditReport.getAuditor().getId() == principalId;

		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
