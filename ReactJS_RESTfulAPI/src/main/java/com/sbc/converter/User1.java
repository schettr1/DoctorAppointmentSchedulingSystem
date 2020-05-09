package com.sbc.converter;

import java.util.Arrays;
import java.util.Date;

/**
 * @author suny4 at 4/10/2020
 * We created User1.class so that we can receive the new doctor/patient JSON data posted 
 * from ReactJs appplication. The JSON data contains parameters such as street, city, state, zipcode,
 * category and degrees[] that are not defined in User.class or Doctor.class alone. 
 * So, we create dummy User1.class that incorporates all parameters of new doctor 
 * JSON data send from ReactJs application in the POST method. Also, it is used
 * when all these parameters are send ReactJs for updating doctor/patient info. 
 */
public class User1 {

	private int id;
	private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int gender;
    private Date birthdate;
    private int category;
    private int[] degrees;
    private String username;
    private String password;
    private String street;
    private String city; 
    private int state;
    private int zipcode;
    
	
		
    public User1() {	
	}
	public User1(int id, String firstname, String lastname, String email, String phone, int gender, Date birthdate,
			int category, int[] degrees, String username, String password, String street, String city, int state,
			int zipcode) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.birthdate = birthdate;
		this.category = category;
		this.degrees = degrees;
		this.username = username;
		this.password = password;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int[] getDegrees() {
		return degrees;
	}
	public void setDegrees(int[] degrees) {
		this.degrees = degrees;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "User1 [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", phone=" + phone + ", gender=" + gender + ", birthdate=" + birthdate + ", category=" + category
				+ ", degrees=" + Arrays.toString(degrees) + ", username=" + username + ", password=" + password
				+ ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + "]";
	}
	

}
