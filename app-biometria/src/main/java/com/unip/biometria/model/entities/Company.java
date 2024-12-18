package com.unip.biometria.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Company")
public class Company {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "Company_id")
	private int id;
	private String name;
	private Integer cnpj;
	private Integer location;
	private String agrotoxic;
	private Double quantity;
	private Date dateOfApplication;
	private String penalties;
	private String observation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCnpj() {
		return cnpj;
	}

	public void setCnpj(int cnpj) {
		this.cnpj = cnpj;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getAgrotoxic() {
		return agrotoxic;
	}

	public void setAgrotoxic(String agrotoxic) {
		this.agrotoxic = agrotoxic;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public String getPenalties() {
		return penalties;
	}

	public void setPenalties(String penalties) {
		this.penalties = penalties;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
