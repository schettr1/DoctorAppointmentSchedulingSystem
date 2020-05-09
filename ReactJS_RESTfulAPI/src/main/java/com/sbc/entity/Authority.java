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
import com.sbc.enums.RoleEnum;


@Entity
@Table(name = "authority")
//prevents fetching child entities when calling parent entity which cause a circular iteration  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})  
public class Authority {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "role")
	private RoleEnum role;

	@ManyToOne
	@JoinColumn(name = "userid_fk")		// @JoinColum will create column-name 'userid_fk' in Role.table
	private User user;

	
	/* constructor and getter & setter */
	public Authority() {		
	}
	
	public Authority(RoleEnum role, User user) {		
		super();
		this.role = role;
		this.user = user;
	}
	
	public Authority(int id, RoleEnum role, User user) {
		super();
		this.id = id;
		this.role = role;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	
}
