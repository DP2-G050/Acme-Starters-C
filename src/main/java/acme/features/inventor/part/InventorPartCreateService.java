
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.entities.invention.PartKind;
import acme.realms.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int inventionId;
		Invention invention;
		Money cost = new Money();

		inventionId = super.getRequest().getData("inventionId", int.class);
		invention = this.repository.findInventionById(inventionId);
		cost.setAmount(0.0);
		cost.setCurrency("EUR");

		this.part = super.newObject(Part.class);
		this.part.setName("");
		this.part.setDescription("");
		this.part.setCost(cost);
		this.part.setInvention(invention);
		;
	}

	@Override
	public void authorise() {
		boolean status;
		int inventionId;
		Invention invention;

		inventionId = super.getRequest().getData("inventionId", int.class);
		invention = this.repository.findInventionById(inventionId);

		status = invention != null && //
			invention.isDraftMode() && invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(PartKind.class, this.part.getKind());

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("inventionId", super.getRequest().getData("inventionId", int.class));
		tuple.put("draftMode", this.part.getInvention().isDraftMode());
		tuple.put("kinds", choices);
	}
}
