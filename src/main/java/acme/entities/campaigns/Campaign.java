
package acme.entities.campaigns;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.entities.milestones.Milestone;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	// Serialisation version --------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------

	@Mandatory
	//	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	//	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	//	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date				endMoment;

	@ValidUrl
	@Column
	private String				moreInfo;

	@NotNull
	@Column
	private Boolean				draftMode;

	// Derived attributes --------------------


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return null;
		long diffInMillies = Math.abs(this.endMoment.getTime() - this.startMoment.getTime());
		long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		// Calculamos meses aproximados (días / 30) y redondeamos a 1 decimal
		double months = diffInDays / 30.0;
		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	@Positive
	public Double getEffort() {
		if (this.milestones == null || this.milestones.isEmpty())
			return 0.0;
		// Suma el esfuerzo de todos los hitos de la lista
		return this.milestones.stream().mapToDouble(Milestone::getEffort).sum();
	}

	// Relationships --------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson			spokesperson;

	// Para calcular esfuerzo, 1 campaña tiene N hitos
	@OneToMany(mappedBy = "campaign")
	private Collection<Milestone>	milestones;

}
