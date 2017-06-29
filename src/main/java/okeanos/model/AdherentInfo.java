package okeanos.model;

import java.util.Date;

public class AdherentInfo {

	private Long id;
	private Long fk_account_id;
	private String firstname;
	private String lastname;
	private Date birsthday;
	private String birthplace;
	private String licence_number;
	private String adresse;
	private String zip_code;
	private String city;
	private String job;
	private String tel_number;
	private String mobile_number;
	private String emergency_contact;
	private String emergency_tel_number;
	private Date createdOn;

	public AdherentInfo(Long id, Long fk_account_id, String firstname, String lastname, Date birsthday,
			String birthplace, String licence_number, String adresse, String zip_code, String city, String job,
			String tel_number, String mobile_number, String emergency_contact, String emergency_tel_number,
			Date createdOn) {
		super();
		this.id = id;
		this.fk_account_id = fk_account_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birsthday = birsthday;
		this.birthplace = birthplace;
		this.licence_number = licence_number;
		this.adresse = adresse;
		this.zip_code = zip_code;
		this.city = city;
		this.job = job;
		this.tel_number = tel_number;
		this.mobile_number = mobile_number;
		this.emergency_contact = emergency_contact;
		this.emergency_tel_number = emergency_tel_number;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirsthday() {
		return birsthday;
	}

	public void setBirsthday(Date birsthday) {
		this.birsthday = birsthday;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getLicence_number() {
		return licence_number;
	}

	public void setLicence_number(String licence_number) {
		this.licence_number = licence_number;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTel_number() {
		return tel_number;
	}

	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmergency_contact() {
		return emergency_contact;
	}

	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
	}

	public String getEmergency_tel_number() {
		return emergency_tel_number;
	}

	public void setEmergency_tel_number(String emergency_tel_number) {
		this.emergency_tel_number = emergency_tel_number;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
