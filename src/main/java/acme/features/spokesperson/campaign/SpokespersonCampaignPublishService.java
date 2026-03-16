
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.isDraftMode() && this.campaign.getSpokesperson().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
		{
			boolean futureMoment;
			futureMoment = MomentHelper.isFuture(this.campaign.getStartMoment()) && MomentHelper.isFuture(this.campaign.getEndMoment());
			super.state(futureMoment, "*", "acme.validation.campaign.no-future.message");
		}
		{

			boolean endMomentAfterStartMoment = true;
			if (this.campaign.getStartMoment() != null && this.campaign.getEndMoment() != null)
				endMomentAfterStartMoment = MomentHelper.isBeforeOrEqual(this.campaign.getStartMoment(), this.campaign.getEndMoment());
			super.state(endMomentAfterStartMoment, "endMoment", "acme.validation.campaign.end-moment-before-start.message");
		}
		{
			Integer milestoneCount = this.repository.countMilestones(this.campaign.getId());
			boolean atLeastOneMilestone = milestoneCount != null && milestoneCount >= 1;

			super.state(atLeastOneMilestone, "*", "acme.validation.campaign.no-parts.message");
		}
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
