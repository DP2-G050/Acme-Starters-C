
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.any.campaign.CampaignRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignDeleteService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	protected CampaignRepository	repository;

	private Campaign				campaign;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findOneCampaignById(id);
	}

	@Override
	public void authorise() {
		int uaId = super.getRequest().getPrincipal().getAccountId();
		boolean isMine = this.campaign != null && this.campaign.getSpokesperson().getUserAccount().getId() == uaId;
		// Solo borrable si es borrador
		super.setAuthorised(isMine && this.campaign.getDraftMode());
	}

	@Override
	public void bind() {
		// No binding
	}

	@Override
	public void validate() {
		// No validation
	}

	@Override
	public void execute() {
		this.repository.delete(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
