
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidString;
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
	@ValidString
	@Column
	private String				bio;

	@Mandatory
	@ValidString
	@Column
	private String				keyWords;

	@Mandatory
	@Column
	private boolean				licensed;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
