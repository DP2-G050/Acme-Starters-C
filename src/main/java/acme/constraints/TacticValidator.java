
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;
import acme.entities.strategy.Tactic;

@Validator
public class TacticValidator extends AbstractValidator<ValidTactic, Tactic> {

	@Autowired
	private StrategyRepository repository;


	@Override
	protected void initialise(final ValidTactic annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Tactic tactic, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (tactic == null)
			result = true;
		else {

			// La suma total de porcentajes no puede superar el 100%
			{
				boolean validTotal = true;

				Strategy strategy = tactic.getStrategy();

				if (strategy != null && tactic.getExpectedPercentage() != null) {

					Double currentTotal = this.repository.computeExpectedPercentage(strategy.getId());
					Double totalWithoutCurrent = currentTotal == null ? 0.0 : currentTotal;

					// Si estamos editando, restamos el valor anterior
					if (tactic.getId() != 0) {
						Double previousValue = this.repository.computeExpectedPercentage(strategy.getId());
						totalWithoutCurrent = totalWithoutCurrent - (previousValue == null ? 0.0 : previousValue);
					}

					double newTotal = totalWithoutCurrent + tactic.getExpectedPercentage();

					validTotal = newTotal <= 100.0;
				}

				super.state(context, validTotal, "expectedPercentage", "acme.validation.tactic.porcentaje-supera-100.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
