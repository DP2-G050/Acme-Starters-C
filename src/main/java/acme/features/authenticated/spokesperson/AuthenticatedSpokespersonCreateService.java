
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonCreateService extends AbstractService<Authenticated, Spokesperson> {

	@Autowired
	protected AuthenticatedSpokespersonRepository	repository;

	private Spokesperson				spokesperson;


	@Override
	public void load() {
		this.spokesperson = new Spokesperson();
		int uaId = super.getRequest().getPrincipal().getAccountId();
		UserAccount ua = this.repository.findUserAccountById(uaId);
		this.spokesperson.setUserAccount(ua);
	}

	@Override
	public void authorise() {
		boolean hasRole = this.repository.findSpokespersonByUserAccountId(super.getRequest().getPrincipal().getAccountId()) != null;
		super.setAuthorised(!hasRole);
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
