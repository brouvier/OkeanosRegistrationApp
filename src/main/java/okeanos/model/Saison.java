package okeanos.model;

import java.util.Date;

public class Saison {

	private Long id;
	private String label;
	private Date start_date;
	private Date end_date;
	private Date createdOn;

	public Saison(Long id, String label, Date start_date, Date end_date, Date createdOn) {
		super();
		this.id = id;
		this.label = label;
		this.start_date = start_date;
		this.end_date = end_date;
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

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
