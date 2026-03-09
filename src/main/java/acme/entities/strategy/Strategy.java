
package acme.entities.strategy;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@ValidStrategy
@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------

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
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------

	@Transient
	@Autowired
	private StrategyRepository	repository;


	@Transient
	public Double getMonthsActive() {
		Double months = 0.0;
		if (this.startMoment != null && this.endMoment != null)
			months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double getExpectedPercentage() {

		Double result = this.repository.computeExpectedPercentage(this.getId());

		return result == null ? 0.0 : result;
	}

	// Relationships --------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser fundraiser;

}
