
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	public void initialize(final ValidAuditReport constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final AuditReport report, final ConstraintValidatorContext context) {
		assert context != null;

		if (report == null)
			return true;

		// Validar ticker único
		if (report.getTicker() != null) {
			AuditReport existing = this.repository.findByTicker(report.getTicker());
			boolean uniqueTicker = existing == null || existing.getId() == report.getId();
			super.state(context, uniqueTicker, "ticker", "acme.validation.auditReport.ticker.non-unique");
		}

		// Validar publicación solo si no está en draftMode
		if (!report.isDraftMode()) {

			// Debe tener al menos una sección
			Integer sectionCount = this.repository.countSections(report.getId());
			boolean hasSections = sectionCount != null && sectionCount > 0;
			super.state(context, hasSections, "draftMode", "acme.validation.auditReport.auditSections.error.message");

			// Validar fechas: intervalo válido y en el futuro respecto a ahora
			Date start = report.getStartMoment();
			Date end = report.getEndMoment();
			Date now = MomentHelper.getCurrentMoment();

			boolean validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			boolean startInFuture = start != null && MomentHelper.isAfter(start, now);
			boolean endInFuture = end != null && MomentHelper.isAfter(end, now);

			super.state(context, validInterval, "startMoment", "acme.validation.auditReport.dates.error");
			super.state(context, startInFuture, "startMoment", "acme.validation.auditReport.startMoment.future");
			super.state(context, endInFuture, "endMoment", "acme.validation.auditReport.endMoment.future");
		}

		return !super.hasErrors(context);
	}
}
