package com.sbc.converter;

/**
 * @author suny4 at 4/14/2020
 * We created AppointmentDetail2.class so that we can receive the AppointmentDetail JSON data put 
 * from ReactJs appplication. AppointmentDetail JSON data contains parameters that are defined below.
 * No other classes has these parameters defined and therefore, we created projection 
 * AppointmentDetail2.class to incorporate all these parameters.
 */
public class AppointmentDetail2 {

	private int appointmentDetailId;
	private String reason;
	private String treatment;
	private String prescription;
	private String note;
	private int appointmentId;
	private int appointmentStatus;
	
	
	public AppointmentDetail2() {
		super();
	}
	public AppointmentDetail2(int appointmentDetailId, String reason, String treatment, String prescription,
			String note, int appointmentId, int appointmentStatus) {
		super();
		this.appointmentDetailId = appointmentDetailId;
		this.reason = reason;
		this.treatment = treatment;
		this.prescription = prescription;
		this.note = note;
		this.appointmentId = appointmentId;
		this.appointmentStatus = appointmentStatus;
	}
	public int getAppointmentDetailId() {
		return appointmentDetailId;
	}
	public void setAppointmentDetailId(int appointmentDetailId) {
		this.appointmentDetailId = appointmentDetailId;
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
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(int appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	@Override
	public String toString() {
		return "AppointmentDetail2 [appointmentDetailId=" + appointmentDetailId + ", reason=" + reason + ", treatment="
				+ treatment + ", prescription=" + prescription + ", note=" + note + ", appointmentId=" + appointmentId
				+ ", appointmentStatus=" + appointmentStatus + "]";
	}
	
}
