
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && //TODO
			(this.invention.getInventor().isPrincipal() || !this.invention.isDraftMode() && //
				MomentHelper.isFuture(this.invention.getStartMoment()) && MomentHelper.isFuture(this.invention.getEndMoment()));

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "inventor", "draftMode");
		super.unbindGlobal("monthsActive", this.invention.getMonthsActive());
		super.unbindGlobal("cost", this.invention.getCost());
	}

}
