package com.sbc.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="Admin")
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User {

	
}
