
package acme.features.any.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		this.auditSection = this.repository.findPublishedAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.auditSection != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");

		int auditReportId = this.auditSection.getReport().getId();
		tuple.put("auditReportId", auditReportId);
	}
}
