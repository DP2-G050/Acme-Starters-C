
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.any.campaign.AnyCampaignRepository;
import acme.features.any.milestone.AnyMilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	protected AnyCampaignRepository		repository;

	private Campaign					campaign;

	@Autowired
	protected AnyMilestoneRepository	milestoneRepository;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findOneCampaignById(id);
	}

	@Override
	public void authorise() {
		int uaId = super.getRequest().getPrincipal().getAccountId();
		boolean isMine = this.campaign != null && this.campaign.getSpokesperson().getUserAccount().getId() == uaId;
		// Solo publicable si es borrador
		super.setAuthorised(isMine && this.campaign.getDraftMode());
	}

	@Override
	public void bind() {
		// No binding
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
		//  Debe tener al menos un hito
		if (!this.milestoneRepository.existsByCampaignId(this.campaign.getId()))
			super.state(false, "draftMode", "campaign.error.no-milestones");
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
