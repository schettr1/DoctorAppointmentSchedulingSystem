package com.sbc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Feedback")
public class Feedback {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="message")
	private String message;
	
	@Column(name="date")
	private Date date;

	public Feedback() {
		super();
	}
	public Feedback(int id, Date date, String message) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", message=" + message + ", date=" + date + "]";
	}
	
	
	
}


