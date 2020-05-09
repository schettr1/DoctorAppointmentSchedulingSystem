package com.sbc.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.sbc.enums.GenderEnum;

@Entity
@Table(name="User")
@Inheritance(strategy=InheritanceType.JOINED)		// TABLE PER SUB-CLASSES
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
    private String lastname;
	
	@Column(name="email")	// In development env., I keep email not unique because I need to send email on successful registration.
    private String email;
	
	@Column(name="phone")
    private String phone;
	
	@OneToOne
	@JoinColumn(name="addressid_fk")
    private Address address;
	
	@Column(name="gender")
    private GenderEnum gender;
	
	@Column(name="birthdate")
    private Date birthdate;
	
	@Column(name="username", unique=true)
    private String username;
	
	@Column(name="password")
    private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "user")	
	private Authority authority;
		
    public User() {	
	}
	public User(int id, String firstname, String lastname, String email, String phone, Address address, GenderEnum gender, 
			Date birthdate, String username, String password, boolean enabled, Authority authority) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.birthdate = birthdate;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authority = authority;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public GenderEnum getGender() {
		return gender;
	}
	public void setGender(GenderEnum gender) {
		this.gender = gender;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", phone=" + phone 
				+ ", address=" + address + ", gender=" + gender + ", birthdate=" + birthdate + ", username=" + username 
				+ ", password=" + password + ", enabled=" + enabled + ", authority=" + authority + "]\n";
	}
	
}
