
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.id = :id")
	Inventor findInventorById(int id);

	@Query("select count(i) from Invention i where (i.inventor.id = :id AND i.draftMode = FALSE)")
	Integer hasInventionsPublished(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
