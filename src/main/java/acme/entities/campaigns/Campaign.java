
package acme.entities.campaigns;

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
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
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

	// Derived attributes --------------------

	@Transient
	@Autowired
	private CampaignRepository	repository;


	public Long getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0l;

		return MomentHelper.computeDuration(this.startMoment, this.endMoment).get(ChronoUnit.MONTHS);

	}

	// Valorar sacarlo de aqui a un servicio
	@Transient
	public Double getEffort() {
		return this.repository == null ? 0.0 : this.repository.calculateEffort(this.getId());
	}

	// Relationships --------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson spokesperson;

}
