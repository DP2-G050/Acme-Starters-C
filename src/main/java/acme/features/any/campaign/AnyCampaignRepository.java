
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	// S2/2: Listar campañas publicadas (Any Principal)
	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findPublishedCampaigns();

	// S2/2: Mostrar detalle de una campaña (Any Principal)
	@Query("select c from Campaign c where c.id = :id")
	Campaign findOneCampaignById(int id);

	// S2/4: Gestionar MIS campañas (Spokesperson)
	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId")
	Collection<Campaign> findManyCampaignsBySpokespersonId(int spokespersonId);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double calculateEffort(int campaignId);

}
