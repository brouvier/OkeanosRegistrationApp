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
	private Boolean nead_certificate;
	// Diving
	private Long fk_actual_training_id;
	private Long fk_training_id;
	// Hockey
	private Long fk_team_id;
	// Diving
	private Long fk_sick_note_id;
	private Long fk_parental_agreement_id;
	// Validation process
	private Boolean validation_start;
	private Boolean validation_general_informations;
	private Boolean validation_licence;
	private Boolean validation_sick_note;
	private Boolean validation_parental_agreement;
	private Boolean validation_payment_transmitted;
	private Boolean validation_payment_cashed;
	private String validation_comment;
	private Date createdOn;

	public AdherentInfoSaison(Long id, Long fk_account_id, Long fk_saison_id, Long fk_ffessm_licence_id,
			Long fk_subscription_id, Long fk_insurance_id, Boolean picture_authorisation, Boolean nead_certificate,
			Long fk_actual_training_id, Long fk_training_id, Long fk_team_id, Long fk_sick_note_id,
			Long fk_parental_agreement_id, Boolean validation_start, Boolean validation_general_informations,
			Boolean validation_licence, Boolean validation_sick_note, Boolean validation_parental_agreement,
			Boolean validation_payment_transmitted, Boolean validation_payment_cashed, String validation_comment,
			Date createdOn) {
		super();
		this.id = id;
		this.fk_account_id = fk_account_id;
		this.fk_saison_id = fk_saison_id;
		this.fk_ffessm_licence_id = fk_ffessm_licence_id;
		this.fk_subscription_id = fk_subscription_id;
		this.fk_insurance_id = fk_insurance_id;
		this.picture_authorisation = picture_authorisation;
		this.nead_certificate = nead_certificate;
		this.fk_actual_training_id = fk_actual_training_id;
		this.fk_training_id = fk_training_id;
		this.fk_team_id = fk_team_id;
		this.fk_sick_note_id = fk_sick_note_id;
		this.fk_parental_agreement_id = fk_parental_agreement_id;
		this.validation_start = validation_start;
		this.validation_general_informations = validation_general_informations;
		this.validation_licence = validation_licence;
		this.validation_sick_note = validation_sick_note;
		this.validation_parental_agreement = validation_parental_agreement;
		this.validation_payment_transmitted = validation_payment_transmitted;
		this.validation_payment_cashed = validation_payment_cashed;
		this.validation_comment = validation_comment;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "AdherentInfoSaison [id=" + id + ", fk_account_id=" + fk_account_id + ", fk_saison_id=" + fk_saison_id
				+ ", fk_ffessm_licence_id=" + fk_ffessm_licence_id + ", fk_subscription_id=" + fk_subscription_id
				+ ", fk_insurance_id=" + fk_insurance_id + ", picture_authorisation=" + picture_authorisation
				+ ", nead_certificate=" + nead_certificate + ", fk_actual_training_id=" + fk_actual_training_id
				+ ", fk_training_id=" + fk_training_id + ", fk_team_id=" + fk_team_id + ", fk_sick_note_id="
				+ fk_sick_note_id + ", fk_parental_agreement_id=" + fk_parental_agreement_id + ", validation_start="
				+ validation_start + ", validation_general_informations=" + validation_general_informations
				+ ", validation_licence=" + validation_licence + ", validation_sick_note=" + validation_sick_note
				+ ", validation_parental_agreement=" + validation_parental_agreement
				+ ", validation_payment_transmitted=" + validation_payment_transmitted + ", validation_payment_cashed="
				+ validation_payment_cashed + ", validation_comment=" + validation_comment + ", createdOn=" + createdOn
				+ "]";
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

	public Long getFk_sick_note_id() {
		return fk_sick_note_id;
	}

	public void setFk_sick_note_id(Long fk_sick_note_id) {
		this.fk_sick_note_id = fk_sick_note_id;
	}

	public Long getFk_parental_agreement_id() {
		return fk_parental_agreement_id;
	}

	public void setFk_parental_agreement_id(Long fk_parental_agreement_id) {
		this.fk_parental_agreement_id = fk_parental_agreement_id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getNead_certificate() {
		return nead_certificate;
	}

	public void setNead_certificate(Boolean nead_certificate) {
		this.nead_certificate = nead_certificate;
	}

	public Boolean getValidation_start() {
		return validation_start;
	}

	public void setValidation_start(Boolean validation_start) {
		this.validation_start = validation_start;
	}

	public Boolean getValidation_general_informations() {
		return validation_general_informations;
	}

	public void setValidation_general_informations(Boolean validation_general_informations) {
		this.validation_general_informations = validation_general_informations;
	}

	public Boolean getValidation_licence() {
		return validation_licence;
	}

	public void setValidation_licence(Boolean validation_licence) {
		this.validation_licence = validation_licence;
	}

	public Boolean getValidation_sick_note() {
		return validation_sick_note;
	}

	public void setValidation_sick_note(Boolean validation_sick_note) {
		this.validation_sick_note = validation_sick_note;
	}

	public Boolean getValidation_parental_agreement() {
		return validation_parental_agreement;
	}

	public void setValidation_parental_agreement(Boolean validation_parental_agreement) {
		this.validation_parental_agreement = validation_parental_agreement;
	}

	public Boolean getValidation_payment_transmitted() {
		return validation_payment_transmitted;
	}

	public void setValidation_payment_transmitted(Boolean validation_payment_transmitted) {
		this.validation_payment_transmitted = validation_payment_transmitted;
	}

	public Boolean getValidation_payment_cashed() {
		return validation_payment_cashed;
	}

	public void setValidation_payment_cashed(Boolean validation_payment_cashed) {
		this.validation_payment_cashed = validation_payment_cashed;
	}

	public String getValidation_comment() {
		return validation_comment;
	}

	public void setValidation_comment(String validation_comment) {
		this.validation_comment = validation_comment;
	}

}
