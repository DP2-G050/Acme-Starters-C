
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {

			// El ticker debe ser único
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.ticker-duplicado.message");
			}

			// Si no está en borrador, debe tener al menos una táctica
			{
				boolean atLeastOneTactic;

				atLeastOneTactic = Boolean.TRUE.equals(strategy.getDraftMode()) || this.repository.countTactics(strategy.getId()) >= 1;

				super.state(context, atLeastOneTactic, "*", "acme.validation.strategy.sin-tacticas.message");
			}

			// La fecha de fin debe ser posterior a la fecha de inicio
			{
				boolean endMomentAfterStartMoment = true;

				if (strategy.getStartMoment() != null && strategy.getEndMoment() != null)
					endMomentAfterStartMoment = Boolean.TRUE.equals(strategy.getDraftMode()) || strategy.getStartMoment().before(strategy.getEndMoment());

				super.state(context, endMomentAfterStartMoment, "*", "acme.validation.strategy.fecha-fin-anterior.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
