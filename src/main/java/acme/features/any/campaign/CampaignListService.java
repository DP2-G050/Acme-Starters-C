
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class CampaignListService extends AbstractService<Any, Campaign> {

	@Autowired
	protected CampaignRepository	repository;

	private Collection<Campaign>	campaigns;


	@Override
	public void load() {
		this.campaigns = this.repository.findPublishedCampaigns();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		// Usamos unbindObjects para colecciones según el framework
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "spokeperson");
	}

}
