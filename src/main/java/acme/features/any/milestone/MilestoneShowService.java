
package acme.features.any.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;

@Service
public class MilestoneShowService extends AbstractService<Any, Milestone> {

	@Autowired
	protected MilestoneRepository	repository;

	private Milestone				milestone;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findOneMilestoneById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

}
