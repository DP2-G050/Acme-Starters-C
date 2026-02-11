
package acme.features.any.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class CampaignShowService extends AbstractService<Any, Campaign> {

	@Autowired
	protected CampaignRepository	repository;

	private Campaign				campaign;


	@Override
	public void load() {
		// Asumimos que Request tiene getData
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findOneCampaignById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.campaign != null && !this.campaign.getDraftMode());
	}

	@Override
	public void unbind() {
		// Usamos unbindObject para la entidad principal
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		// Usamos unbindGlobal para los atributos calculados/extra
		super.unbindGlobal("monthsActive", this.campaign.getMonthsActive());
		super.unbindGlobal("effort", this.campaign.getEffort());
	}

}
