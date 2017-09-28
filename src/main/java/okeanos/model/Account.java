package okeanos.model;

import java.util.Date;

public class Account {

	private Long id;
	private String mail;
	private String salt;
	private String password;
	private Boolean admin;
	private Date createdOn;

	public Account(Long id, String mail, String salt, String password, Boolean admin, Date createdOn) {
		this.id = id;
		this.mail = mail;
		this.salt = salt;
		this.password = password;
		this.admin = admin;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", mail=" + mail + ", admin=" + admin + ", createdOn=" + createdOn + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
