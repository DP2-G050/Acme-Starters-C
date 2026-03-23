
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
		{
			boolean futureMoment = true;

			if (this.strategy.getStartMoment() != null && this.strategy.getEndMoment() != null)
				futureMoment = MomentHelper.isFuture(this.strategy.getStartMoment()) && MomentHelper.isFuture(this.strategy.getEndMoment());

			super.state(futureMoment, "*", "acme.validation.strategy.no-future.message");
		}
		{
			boolean endMomentAfterStartMoment = true;
			if (this.strategy.getStartMoment() != null && this.strategy.getEndMoment() != null)
				endMomentAfterStartMoment = MomentHelper.isBeforeOrEqual(this.strategy.getStartMoment(), this.strategy.getEndMoment());
			super.state(endMomentAfterStartMoment, "endMoment", "acme.validation.strategy.end-moment-before-start.message");
		}
		{
			Integer tacticCount = this.repository.countTactics(this.strategy.getId());
			boolean atLeastOneTactic = tacticCount != null && tacticCount >= 1;

			super.state(atLeastOneTactic, "*", "acme.validation.invention.no-parts.message");
		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
