/*
 * MilestoneValidator.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.campaigns.Milestone;

@Validator
public class MilestoneValidator extends AbstractValidator<ValidMilestone, Milestone> {

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidMilestone annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Milestone milestone, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (milestone == null)
			result = true;
		else {
			{

				//				super.state(context, error, "*", "acme.validation.milestone.error.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
