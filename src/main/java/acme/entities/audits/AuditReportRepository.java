
package acme.entities.audits;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(s.hours) from AuditSection s where s.report.id = :id")
	Integer computeHours(int id);

	@Query("select count(s) from AuditSection s where s.report.id = :id")
	Integer countSections(int id);

	@Query("select a from AuditReport a where a.ticker = :ticker")
	AuditReport findByTicker(String ticker);

}
