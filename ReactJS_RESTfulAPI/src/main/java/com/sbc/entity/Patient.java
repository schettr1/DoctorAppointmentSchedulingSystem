package com.sbc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.sbc.enums.InsuranceEnum;

@Entity
@Table(name="Patient")
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User {

	@Column(name="insurance")
	private InsuranceEnum insurance;

	
	public Patient() {
		super();
	}
	public Patient(InsuranceEnum insurance) {
		super();
		this.insurance = insurance;
	}
	public InsuranceEnum getInsurance() {
		return insurance;
	}
	public void setInsurance(InsuranceEnum insurance) {
		this.insurance = insurance;
	}
	@Override
	public String toString() {
		return "Patient [insurance=" + insurance + "]";
	}
	
	
}
