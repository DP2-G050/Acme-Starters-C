
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	// Contar hitos
	@Query("SELECT count(m) > 0 FROM Milestone m WHERE m.campaign.id = :campaignId")
	boolean existsByCampaignId(int campaignId);

}
