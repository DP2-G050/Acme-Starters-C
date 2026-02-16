
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	// @ValidText
	@Column
	private String				bio;

	@Mandatory
	// @ValidText
	@Column
	private String				keyWords;

	@Mandatory
	//@Valid
	@Column
	private boolean				licensed;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
