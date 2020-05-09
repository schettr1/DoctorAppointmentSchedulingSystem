package com.sbc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbc.enums.StateEnum;

@Entity
@Table(name="Address")
//prevents fetching child entities when calling parent entity which cause a circular iteration  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})    
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="street")
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private StateEnum state;
	
	@Column(name="zipcode")
	private int zipcode;
	
	@OneToOne(mappedBy="address")  
	private User user;
	

	public Address() {
		
	}
	public Address(int id, String street, String city, StateEnum state, int zipcode, User user) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public StateEnum getState() {
		return state;
	}
	public void setState(StateEnum state) {
		this.state = state;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode="
				+ zipcode + ", user=" + user + "]";
	}
	
	
}
