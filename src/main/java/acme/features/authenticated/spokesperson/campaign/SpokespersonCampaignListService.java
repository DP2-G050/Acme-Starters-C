
package acme.features.authenticated.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.any.campaign.CampaignRepository;
import acme.features.authenticated.spokesperson.AuthenticatedSpokespersonRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignListService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	protected CampaignRepository		repository;

	@Autowired
	protected AuthenticatedSpokespersonRepository	spokespersonRepository;

	private Collection<Campaign>		campaigns;


	@Override
	public void load() {
		int uaId = super.getRequest().getPrincipal().getAccountId();
		Spokesperson s = this.spokespersonRepository.findSpokespersonByUserAccountId(uaId);
		this.campaigns = this.repository.findManyCampaignsBySpokespersonId(s.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "draftMode");
	}

}
