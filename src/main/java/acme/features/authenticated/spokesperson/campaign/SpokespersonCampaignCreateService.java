
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.any.campaign.CampaignRepository;
import acme.features.authenticated.spokesperson.AuthenticatedSpokespersonRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	protected CampaignRepository		repository;

	@Autowired
	protected AuthenticatedSpokespersonRepository	spokespersonRepository;

	private Campaign					campaign;


	@Override
	public void load() {
		this.campaign = new Campaign();
		this.campaign.setDraftMode(true); // Siempre empieza como borrador

		int uaId = super.getRequest().getPrincipal().getAccountId();
		Spokesperson s = this.spokespersonRepository.findSpokespersonByUserAccountId(uaId);
		this.campaign.setSpokesperson(s);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
