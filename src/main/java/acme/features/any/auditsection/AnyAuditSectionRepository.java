
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.report.id = :auditReportId and s.report.draftMode = false")
	Collection<AuditSection> findPublishedAuditSectionsByAuditReportId(int auditReportId);

	@Query("select s from AuditSection s where s.id = :id and s.report.draftMode = false")
	AuditSection findPublishedAuditSectionById(int id);

}
