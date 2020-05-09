package com.sbc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbc.enums.StatusEnum;

@Entity
@Table(name="Appointment")
//prevents fetching child entities when calling parent entity which cause a circular iteration
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "appointmentdetail"})  
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="starttime")
	private Date starttime;
	
	@Column(name="endtime")
	private Date endtime;
	
	@Column(name="status")
	private StatusEnum status;
	
	@OneToOne
	@JoinColumn(name="appointmentdetailid_fk")
	private AppointmentDetail appointmentdetail;
	
	@Column(name="patientid_fk")
	private int patientid_fk;
	
	@Column(name="doctorid_fk")
	private int doctorid_fk;

	
	
	public Appointment() {
		super();
	}
	public Appointment(int id, Date starttime, Date endtime, StatusEnum status, AppointmentDetail appointmentdetail,
			int patientid_fk, int doctorid_fk) {
		super();
		this.id = id;
		this.starttime = starttime;
		this.endtime = endtime;
		this.status = status;
		this.appointmentdetail = appointmentdetail;
		this.patientid_fk = patientid_fk;
		this.doctorid_fk = doctorid_fk;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public AppointmentDetail getAppointmentdetail() {
		return appointmentdetail;
	}
	public void setAppointmentdetail(AppointmentDetail appointmentdetail) {
		this.appointmentdetail = appointmentdetail;
	}
	public int getPatientid_fk() {
		return patientid_fk;
	}
	public void setPatientid_fk(int patientid_fk) {
		this.patientid_fk = patientid_fk;
	}
	public int getDoctorid_fk() {
		return doctorid_fk;
	}
	public void setDoctorid_fk(int doctorid_fk) {
		this.doctorid_fk = doctorid_fk;
	}
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", starttime=" + starttime + ", endtime=" + endtime + ", appointmentdetail="
				+ appointmentdetail + ", patientid_fk=" + patientid_fk + ", doctorid_fk=" + doctorid_fk + "]";
	}
	
	
	
	
}
