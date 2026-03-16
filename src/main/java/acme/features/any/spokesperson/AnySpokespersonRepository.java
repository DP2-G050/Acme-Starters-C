
package acme.features.any.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Spokesperson;

@Repository
public interface AnySpokespersonRepository extends AbstractRepository {

	@Query("select s from Spokesperson s where s.id = :id")
	Spokesperson findSpokespersonById(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select (count(c) > 0) from Campaign c where c.spokesperson.id = :id and c.draftMode = false")
	boolean hasPublishedCampaigns(int id);

}
