package com.sbc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Rating")
//prevents fetching child entities when calling parent entity which cause a circular iteration  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "doctor"})  
public class Rating {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="points")
	private int points;
	
	@Column(name="comments")
	private String comments;
	
	@ManyToOne
	@JoinColumn(name = "doctorid_fk")
	private Doctor doctor;
	
	
	
	public Rating() {
		super();
	}
	public Rating(int id, int points, String comments, Doctor doctor) {
		super();
		this.id = id;
		this.points = points;
		this.comments = comments;
		this.doctor = doctor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	@Override
	public String toString() {
		return "Rating [id=" + id + ", points=" + points + ", comments=" + comments + ", doctor=" + doctor + "]";
	}

	
	
}
