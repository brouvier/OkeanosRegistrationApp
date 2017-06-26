package okeanos.model;

import java.util.Date;

public class AdherentInfoSaison {

	private Long id;
	private Long fk_account_id;
	private Long fk_saison_id;
	private Long fk_ffessm_licence_id;
	private Long fk_subscription_id;
	private Long fk_insurance_id;
	private Boolean picture_authorisation;
	// Diving
	private Long fk_actual_training_id;
	private Long fk_training_id;
	// Hockey
	private Long fk_team_id;
	private Date createdOn;

	public AdherentInfoSaison(Long id, Long fk_account_id, Long fk_saison_id, Long fk_ffessm_licence_id,
			Long fk_subscription_id, Long fk_insurance_id, Boolean picture_authorisation, Long fk_actual_training_id,
			Long fk_training_id, Long fk_team_id, Date createdOn) {
		super();
		this.id = id;
		this.fk_account_id = fk_account_id;
		this.fk_saison_id = fk_saison_id;
		this.fk_ffessm_licence_id = fk_ffessm_licence_id;
		this.fk_subscription_id = fk_subscription_id;
		this.fk_insurance_id = fk_insurance_id;
		this.picture_authorisation = picture_authorisation;
		this.fk_actual_training_id = fk_actual_training_id;
		this.fk_training_id = fk_training_id;
		this.fk_team_id = fk_team_id;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFk_account_id() {
		return fk_account_id;
	}

	public void setFk_account_id(Long fk_account_id) {
		this.fk_account_id = fk_account_id;
	}

	public Long getFk_saison_id() {
		return fk_saison_id;
	}

	public void setFk_saison_id(Long fk_saison_id) {
		this.fk_saison_id = fk_saison_id;
	}

	public Long getFk_ffessm_licence_id() {
		return fk_ffessm_licence_id;
	}

	public void setFk_ffessm_licence_id(Long fk_ffessm_licence_id) {
		this.fk_ffessm_licence_id = fk_ffessm_licence_id;
	}

	public Long getFk_subscription_id() {
		return fk_subscription_id;
	}

	public void setFk_subscription_id(Long fk_subscription_id) {
		this.fk_subscription_id = fk_subscription_id;
	}

	public Long getFk_insurance_id() {
		return fk_insurance_id;
	}

	public void setFk_insurance_id(Long fk_insurance_id) {
		this.fk_insurance_id = fk_insurance_id;
	}

	public Boolean getPicture_authorisation() {
		return picture_authorisation;
	}

	public void setPicture_authorisation(Boolean picture_authorisation) {
		this.picture_authorisation = picture_authorisation;
	}

	public Long getFk_actual_training_id() {
		return fk_actual_training_id;
	}

	public void setFk_actual_training_id(Long fk_actual_training_id) {
		this.fk_actual_training_id = fk_actual_training_id;
	}

	public Long getFk_training_id() {
		return fk_training_id;
	}

	public void setFk_training_id(Long fk_training_id) {
		this.fk_training_id = fk_training_id;
	}

	public Long getFk_team_id() {
		return fk_team_id;
	}

	public void setFk_team_id(Long fk_team_id) {
		this.fk_team_id = fk_team_id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
