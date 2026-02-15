
package acme.entities.strategies;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.entities.tactics.Tactic;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------

	@Mandatory
	// @ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	// @ValidHeader
	@Column
	private String				name;

	@Mandatory
	// @ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Moment				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Relationships --------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser			fundraiser;

	@Valid
	@OneToMany(mappedBy = "strategy")
	private Collection<Tactic>	tactics;

	// Derived attributes -----------------------------------------


	@Transient
	public Double getMonthsActive() {

		if (this.startMoment == null || this.endMoment == null)
			return null;

		long diff = this.endMoment.getTime() - this.startMoment.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		double months = days / 30.0;

		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double getExpectedPercentage() {

		if (this.tactics == null || this.tactics.isEmpty())
			return 0.0;

		return this.tactics.stream().mapToDouble(Tactic::getExpectedPercentage).sum();
	}

}
