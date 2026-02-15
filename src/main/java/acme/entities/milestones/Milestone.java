
package acme.entities.milestones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.entities.campaigns.Campaign;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity {

	// Serialisation version --------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------

	@Mandatory
	//@ValidHeader
	@Column
	private String				title;

	@Mandatory
	//@ValidText
	@Column
	private String				achievements;

	@NotNull
	@Min(0)
	@Column
	private Double				effort;

	@NotNull
	@Column
	private MilestoneKind		kind;

	// Derived attributes --------------------

	// Relationships --------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Campaign			campaign;

}
