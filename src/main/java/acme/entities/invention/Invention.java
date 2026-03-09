
package acme.entities.invention;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	//@Valid
	@Column
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private InventionRepository	repository;


	@Mandatory
	@ValidNumber
	@Transient
	public Double getMonthsActive() {
		Double months = 0.0;
		if (this.startMoment != null && this.endMoment != null)
			months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
		return MathHelper.roundOff(months, 1);
	}

	@Mandatory
	@ValidMoney
	@Transient
	public Money getCost() {
		Money result = new Money();
		Double amount = 0.0;
		if (this.repository.computeCost(this.getId()) != null)
			amount = this.repository.computeCost(this.getId());
		result.setAmount(amount);
		result.setCurrency("EUR");
		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor inventor;

}
