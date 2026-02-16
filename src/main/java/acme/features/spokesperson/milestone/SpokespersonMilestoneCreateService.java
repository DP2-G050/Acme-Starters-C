
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.milestones.Milestone;
import acme.features.any.campaign.CampaignRepository;
import acme.features.any.milestone.MilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	protected MilestoneRepository	repository;

	@Autowired
	protected CampaignRepository	campaignRepository;

	private Milestone				milestone;


	@Override
	public void load() {
		// 1. Recuperamos el ID de la campaña padre desde la URL (masterId)
		int campaignId = super.getRequest().getData("masterId", int.class);

		// 2. Buscamos la campaña
		Campaign campaign = this.campaignRepository.findOneCampaignById(campaignId);

		// 3. Instanciamos el hito y le asignamos la campaña
		this.milestone = new Milestone();
		this.milestone.setCampaign(campaign);
	}

	@Override
	public void authorise() {
		// Verificamos que la campaña existe, pertenece al Spokesperson y NO está publicada (es Draft)
		int uaId = super.getRequest().getPrincipal().getAccountId();
		Campaign c = this.milestone.getCampaign();

		boolean isMine = c != null && c.getSpokesperson().getUserAccount().getId() == uaId;
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
