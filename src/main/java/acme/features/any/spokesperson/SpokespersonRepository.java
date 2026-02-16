
package acme.features.any.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Spokesperson;

@Repository
public interface SpokespersonRepository extends AbstractRepository {

	// Parte requisito S2/2
	@Query("select s from Spokesperson s where s.id = :id")
	Spokesperson findOneSpokespersonById(int id);

}
