
package acme.features.auditor.auditReport;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		if (this.auditReport != null && this.auditReport.isDraftMode()) {
			int principalId = super.getRequest().getPrincipal().getActiveRealm().getId();
			status = this.auditReport.getAuditor() != null && this.auditReport.getAuditor().getId() == principalId;
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);

		// Debe tener al menos una sección
		Collection<AuditSection> sections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
		boolean hasSections = sections != null && !sections.isEmpty();
		super.state(hasSections, "hours", "acme.validation.auditReport.auditSections.error.message");

		// Validar intervalo de fechas
		Date start = this.auditReport.getStartMoment();
		Date end = this.auditReport.getEndMoment();
		boolean validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
		super.state(validInterval, "startMoment", "acme.validation.auditReport.dates.error");

		// Validar que las fechas estén en el futuro respecto al momento actual
		Date now = MomentHelper.getCurrentMoment();
		boolean startInFuture = start != null && MomentHelper.isAfter(start, now);
		boolean endInFuture = end != null && MomentHelper.isAfter(end, now);

		super.state(startInFuture, "startMoment", "acme.validation.auditReport.startMoment.future");
		super.state(endInFuture, "endMoment", "acme.validation.auditReport.endMoment.future");
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("draftMode", this.auditReport.isDraftMode());
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());
	}
}
