package okeanos.model;

import java.util.Date;

public class Insurance {

	private Long id;
	private Long fk_saison_id;
	private String label;
	private Double price;
	private Date createdOn;

	public Insurance(Long id, Long fk_saison_id, String label, Double price, Date createdOn) {
		super();
		this.id = id;
		this.fk_saison_id = fk_saison_id;
		this.label = label;
		this.price = price;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFk_saison_id() {
		return fk_saison_id;
	}

	public void setFk_saison_id(Long fk_saison_id) {
		this.fk_saison_id = fk_saison_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
