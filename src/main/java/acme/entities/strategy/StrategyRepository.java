
package acme.entities.strategy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	int countTactics(Integer strategyId);

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double computeExpectedPercentage(Integer strategyId);

}
