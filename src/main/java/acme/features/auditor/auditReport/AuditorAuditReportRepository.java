
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select r from AuditReport r where r.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select r from AuditReport r where r.auditor.id = :auditorId")
	Collection<AuditReport> findAuditReportsByAuditorId(int auditorId);

	@Query("select s from AuditSection s where s.report.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("select sum(s.hours) from AuditSection s where s.report.id = :auditReportId")
	Integer computeHoursByAuditReportId(int auditReportId);
}
