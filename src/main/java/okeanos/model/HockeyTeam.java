package okeanos.model;

import java.util.Date;

public class HockeyTeam {

	private Long id;
	private String label;
	private Date createdOn;

	public HockeyTeam(Long id, String label, Date createdOn) {
		super();
		this.id = id;
		this.label = label;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
