
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

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

		status = this.invention != null && this.invention.isDraftMode() && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		{
			boolean futureMoment = true;

			if (this.invention.getStartMoment() != null && this.invention.getEndMoment() != null)
				futureMoment = MomentHelper.isFuture(this.invention.getStartMoment()) && MomentHelper.isFuture(this.invention.getEndMoment());

			super.state(futureMoment, "*", "acme.validation.invention.no-future.message");
		}
		{

			boolean endMomentAfterStartMoment = true;
			if (this.invention.getStartMoment() != null && this.invention.getEndMoment() != null)
				endMomentAfterStartMoment = MomentHelper.isBeforeOrEqual(this.invention.getStartMoment(), this.invention.getEndMoment());
			super.state(endMomentAfterStartMoment, "endMoment", "acme.validation.invention.end-moment-before-start.message");
		}
		{
			Integer partCount = this.repository.countParts(this.invention.getId());
			boolean atLeastOnePart = partCount != null && partCount >= 1;

			super.state(atLeastOnePart, "*", "acme.validation.invention.no-parts.message");
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
