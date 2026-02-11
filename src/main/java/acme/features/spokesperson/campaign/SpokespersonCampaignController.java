
package acme.features.spokesperson.campaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Campaign;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignCreateService;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignDeleteService;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignListService;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignPublishService;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignShowService;
import acme.features.authenticated.spokesperson.campaign.SpokespersonCampaignUpdateService;
import acme.realms.Spokesperson;

@Controller
public class SpokespersonCampaignController extends AbstractController<Spokesperson, Campaign> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", SpokespersonCampaignListService.class);
		super.addBasicCommand("show", SpokespersonCampaignShowService.class);
		super.addBasicCommand("create", SpokespersonCampaignCreateService.class);
		super.addBasicCommand("update", SpokespersonCampaignUpdateService.class);
		super.addBasicCommand("delete", SpokespersonCampaignDeleteService.class);

		// "publish" es una acción custom que se comporta como un "update"
		super.addCustomCommand("publish", "update", SpokespersonCampaignPublishService.class);
	}

}
