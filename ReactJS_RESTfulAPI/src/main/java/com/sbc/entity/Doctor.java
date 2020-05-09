package com.sbc.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.sbc.enums.CategoryEnum;
import com.sbc.enums.DegreeEnum;

@Entity
@Table(name="Doctor")
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User {
    
	/**
	 * Because 'degrees' is a set of Enums rather than Objects or Entities, we can use these 4 annotations  
	 * and Hibernate will create an additional table 'Degrees' with columns (doctor_id, degree) 
	 */
	@ElementCollection(targetClass=DegreeEnum.class)
	@Enumerated					 						// For String use @Enumerated(EnumType.STRING) 
	@CollectionTable(name="degrees")					// table name 
	@Column(name="degree") 								// column name in 'degrees' table 
	private Set<DegreeEnum> degrees;
	
	@Column(name="category")
	private CategoryEnum category;
	
	@OneToMany(cascade={
			CascadeType.MERGE, 
			CascadeType.REMOVE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH}, 
			mappedBy = "doctor")	
	private Set<Rating> rating;
	
	public Doctor() {
		super();
	}
	public Doctor(Set<DegreeEnum> degrees, CategoryEnum category, Set<Rating> rating) {
		super();
		this.degrees = degrees;
		this.category = category;
		this.rating = rating;
	}
	
	public Set<DegreeEnum> getDegrees() {
		return degrees;
	}
	public void setDegrees(Set<DegreeEnum> degrees) {
		this.degrees = degrees;
	}
	public CategoryEnum getCategory() {
		return category;
	}
	public void setCategory(CategoryEnum category) {
		this.category = category;
	}
	public Set<Rating> getRating() {
		return rating;
	}
	public void setRating(Set<Rating> rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Doctor [degrees=" + degrees + ", category=" + category + ", rating=" + rating + "]";
	}


}
