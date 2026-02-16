
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.features.any.milestone.MilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneUpdateService extends AbstractService<Spokesperson, Milestone> {

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
		if (this.milestone == null) {
			super.setAuthorised(false);
			return;
		}

		int uaId = super.getRequest().getPrincipal().getAccountId();
		Campaign c = this.milestone.getCampaign();

		// Solo se puede editar si la campaña es mía y sigue en borrador
		boolean isMine = c.getSpokesperson().getUserAccount().getId() == uaId;
		super.setAuthorised(isMine && c.getDraftMode());
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

}
