package com.sbc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="AppointmentDetail")
//prevents fetching child entities when calling parent entity which cause a circular iteration
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "appointment"})  
public class AppointmentDetail {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="treatment")
	private String treatment;
	
	@Column(name="prescription")
	private String prescription;
	
	@Column(name="note")
	private String note;
	
	@OneToOne(mappedBy="appointmentdetail")  
	private Appointment appointment;

	
	
	public AppointmentDetail() {
		super();
	}
	public AppointmentDetail(int id, String reason, String treatment, String prescription, String note,
			Appointment appointment) {
		super();
		this.id = id;
		this.reason = reason;
		this.treatment = treatment;
		this.prescription = prescription;
		this.note = note;
		this.appointment = appointment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
		
}
