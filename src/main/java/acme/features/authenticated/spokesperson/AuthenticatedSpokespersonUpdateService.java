
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonUpdateService extends AbstractService<Authenticated, Spokesperson> {

	@Autowired
	protected AuthenticatedSpokespersonRepository	repository;

	private Spokesperson				spokesperson;


	@Override
	public void load() {
		int uaId = super.getRequest().getPrincipal().getAccountId();
		this.spokesperson = this.repository.findSpokespersonByUserAccountId(uaId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.spokesperson != null);
	}

	@Override
	public void bind() {
		super.bindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.spokesperson);
	}

	@Override
	public void execute() {
		this.repository.save(this.spokesperson);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

}
