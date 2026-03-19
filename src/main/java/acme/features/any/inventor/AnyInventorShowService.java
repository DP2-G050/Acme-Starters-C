
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

@Service
public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyInventorRepository	repository;

	private Inventor				inventor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.inventor = this.repository.findInventorById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getData("id", int.class);
		status = this.inventor != null;
		if (this.repository.hasInventionsPublished(id) != null)
			status = this.repository.hasInventionsPublished(id) >= 1;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
	}

}
