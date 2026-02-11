
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.milestones.Milestone;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	// S2/2 y S2/4: Listar hitos de una campaña concreta
	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findManyMilestonesByCampaignId(int campaignId);

	// S2/2 y S2/4: Mostrar detalle de un hito
	@Query("select m from Milestone m where m.id = :id")
	Milestone findOneMilestoneById(int id);

}
