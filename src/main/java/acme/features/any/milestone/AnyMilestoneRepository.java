
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findMilestoneById(int id);

	// ============================= //

	// S2/2 y S2/4: Listar hitos de una campaña concreta
	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findManyMilestonesByCampaignId(int campaignId);

	@Query("SELECT count(m) FROM Milestone m WHERE m.campaign.id = :campaignId")
	Long countMilestonesByCampaignId(int campaignId);

	@Query("SELECT count(m) > 0 FROM Milestone m WHERE m.campaign.id = :campaignId")
	boolean existsByCampaignId(int campaignId);

}
