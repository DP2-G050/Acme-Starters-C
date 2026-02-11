
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.any.campaign.CampaignRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignShowService extends AbstractService<Spokesperson, Campaign> {

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
		// Solo autoriza si la campaña existe y pertenece al usuario logueado
		super.setAuthorised(this.campaign != null && this.campaign.getSpokesperson().getUserAccount().getId() == uaId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

		// Atributos derivados pasados manualmente
		super.unbindGlobal("monthsActive", this.campaign.getMonthsActive());
		super.unbindGlobal("effort", this.campaign.getEffort());
	}

}
