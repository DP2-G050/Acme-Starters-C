
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.milestones.Milestone;

@Service
public class MilestoneListService extends AbstractService<Any, Milestone> {

	@Autowired
	protected MilestoneRepository	repository;

	private Collection<Milestone>	milestones;


	@Override
	public void load() {
		int campaignId = super.getRequest().getData("masterId", int.class);
		this.milestones = this.repository.findManyMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		// UnbindObjects para listas
		super.unbindObjects(this.milestones, "title", "achievements", "effort", "kind");
	}

}
