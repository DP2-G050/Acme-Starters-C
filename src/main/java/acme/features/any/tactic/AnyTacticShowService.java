
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategy.Tactic;
import acme.entities.strategy.TacticKind;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTacticRepository	repository;

	private Tactic				tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic != null && !this.tactic.getStrategy().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		SelectChoices choices;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		super.getResponse().addGlobal("kindChoices", choices);

		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

}
