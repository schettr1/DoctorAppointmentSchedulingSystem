package com.sbc.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbc.converter.User1;
import com.sbc.entity.Address;
import com.sbc.entity.Authority;
import com.sbc.entity.Doctor;
import com.sbc.entity.Rating;
import com.sbc.entity.User;
import com.sbc.enums.CategoryEnum;
import com.sbc.enums.DegreeEnum;
import com.sbc.enums.GenderEnum;
import com.sbc.enums.RoleEnum;
import com.sbc.enums.StateEnum;
import com.sbc.exception.DoctorNotFoundException;
import com.sbc.exception.DuplicateUsernameException;
import com.sbc.projection.Doctor1;
import com.sbc.projection.Doctor2;
import com.sbc.projection.Doctor3;
import com.sbc.projection.Doctor4;
import com.sbc.repository.AddressRepository;
import com.sbc.repository.AuthorityRepository;
import com.sbc.repository.DoctorRepository;
import com.sbc.repository.RatingRepository;
import com.sbc.repository.UserRepository;

@Service	
@Transactional
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(DoctorService.class);
	
	/**
	 *  SAVE DOCTOR
	 * @param User1
	 * @return Doctor2
	 * First save Address, second save Doctor(enable User) third save Authority and fourth save Rating. If you do not shave Rating, you won't see this doctor on the list of all doctors
	 * because the Query for all doctors contains table Rating and if doctor has no fk on Rating table, that doctor is not found.
	 * User.table (id, firstname, lastname, email, phone, gender, birthdate, username, password, addressid_fk) 
	 * Doctor.table (id, categoryid_fk)
	 * You don't have to create User object to save it to User.table. You can create Doctor object and
	 * override setter-getter methods of User.class to set values. When you save Doctor object, records 
	 * will be saved in both User.table and Doctor.table
	 **/
	public User saveDoctor(User1 user1) {
		
		// check whether username exists already before creating new User
		User user = userRepository.findByUsername(user1.getUsername());
		if(user != null)
			throw new DuplicateUsernameException("username already exist");
		
		
		Address address = new Address();
		address.setStreet(user1.getStreet());
		address.setCity(user1.getCity());
		address.setState(StateEnum.getEnum(user1.getState())); // convert String to Integer and call getEnum() of StateEnum
		address.setZipcode(user1.getZipcode());
		Address savedAddress = addressRepository.save(address);		// 1. SAVES ADDRESS TO ADDRESS.TABLE	
		
		Doctor doctor = new Doctor();
		doctor.setFirstname(user1.getFirstname());
		doctor.setLastname(user1.getLastname());
		doctor.setEmail(user1.getEmail());
		doctor.setPhone(user1.getPhone());
		doctor.setGender(GenderEnum.getEnum(user1.getGender())); 	// convert String to Integer and to Enum by calling getEnum()
		doctor.setBirthdate(user1.getBirthdate());
		doctor.setUsername(user1.getUsername());
		doctor.setPassword(getPasswordEncoder(user1.getPassword()));
		doctor.setAddress(savedAddress);
		doctor.setCategory(CategoryEnum.getEnum(user1.getCategory())); 	// convert Integer to Enum by calling getEnum() of CategoryEnum
		Set<DegreeEnum> degrees = new LinkedHashSet<>();
		for (int i = 0; i < user1.getDegrees().length; i++) { 
            // accessing each element of array 
            DegreeEnum degreeEnum = DegreeEnum.getEnum(user1.getDegrees()[i]); 	// convert String to Integer and to Enum by calling getEnum() 
    		degrees.add(degreeEnum);
        } 
		doctor.setEnabled(true);	// enable User
		doctor.setDegrees(degrees);
		Doctor savedDoctor = doctorRepository.save(doctor);		// 2. SAVES BOTH USER AND DOCTOR TO USER.TABLE AND DOCTOR.TABLE
		
		Authority authority = new Authority(RoleEnum.ROLE_DOCTOR, savedDoctor);
		authorityRepository.save(authority);					// 3. SAVES AUTHORITY TO AUTHORITY.TABLE
		
		Rating rating = new Rating();
		rating.setDoctor(savedDoctor);
		ratingRepository.save(rating);						// 4. SAVE RATING TO RATING.TABLE
		
		return savedDoctor;		// 'upcasting' User savedDoctor = new Doctor(); 
	}

	
	/**
	 *  FIND DOCTOR BY ID - used in creating _links
	 * @param id
	 * @return Doctor
	 */
	public Doctor getOnly(int id) {
		return doctorRepository.findById(id)
				.orElseThrow(
						() -> new DoctorNotFoundException("id=" + id + " cannot be found."));
	}

	
	/**
	 *  FIND DOCTOR BY ID - used to retrieve doctor data for updating purpose
	 * @param id
	 * @return Doctor4
	 */
	public Doctor4 getDoctorById(int id) {
		doctorRepository.findById(id)
				.orElseThrow(
						() -> new DoctorNotFoundException("id=" + id + " cannot be found."));
		Doctor4 doctor = doctorRepository.findDoctorById(id);
		return doctor;
	}
	
	/**
	 *  UPDAYE DOCTOR BY ID
	 * @param User1 user1
	 * @param id
	 * @return User1
	 * /**
	 * @author suny4 at 4/10/2020
	 * Because have not defined CascadeTypes [ALL, PERSIST, DELETE etc] between Doctor-Address
	 * one-to-one mapping, we therefore have to retrieve the Address from the database and persist the values to
	 * Address.table. Had we define CascadeType.PERSIST, we could have persisted values in Address.table
	 * by using setAddress() method of Doctor.class
	 */
	public User1 update(User1 user1, int id) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new DoctorNotFoundException("id=" + id + " cannot be found."));
		
		doctor.setFirstname(user1.getFirstname());
		doctor.setLastname(user1.getLastname());
		doctor.setEmail(user1.getEmail());
		doctor.setPhone(user1.getPhone());
		doctor.setGender(GenderEnum.getEnum(user1.getGender())); 	// convert String to Integer and to Enum by calling getEnum() 
		doctor.setBirthdate(user1.getBirthdate());
		doctor.setUsername(user1.getUsername());
		doctor.setPassword(user1.getPassword());
		doctor.setCategory(CategoryEnum.getEnum(user1.getCategory())); 	// convert Integer to Enum by calling getEnum() of CategoryEnum
		Set<DegreeEnum> degreeEnums = new LinkedHashSet<>();
		for (int i = 0; i < user1.getDegrees().length; i++) { 
            // accessing each String item of array 
            DegreeEnum degreeEnum = DegreeEnum.getEnum(user1.getDegrees()[i]); 	// convert String to Integer and to Enum by calling getEnum() 
            degreeEnums.add(degreeEnum);
        } 
		doctor.setDegrees(degreeEnums);
		
		/*
		 * Since CascadeType is not CascadeType.ALL, we have to save Address object but we can 
		 * persist the doctor object. If you try to LOG doctor.toString(), it will create a large 
		 * iteration of objects and crash the server. So, avoid returning Doctor Object or priting it.
		 */
		Address address = addressRepository.findById(doctor.getAddress().getId())
				.orElseThrow(() -> new DoctorNotFoundException("id=" + id + " cannot be found."));
		address.setStreet(user1.getStreet());
		address.setCity(user1.getCity());
		address.setState(StateEnum.getEnum(user1.getState())); // convert String to Integer and to Enum using getEnum()
		address.setZipcode(user1.getZipcode());
		 
		return user1;	// return User1 instead of Doctor because returning Doctor type will create infinite loops because of circular-mapping
	}

	
	/**
	 *  LIST ALL DOCTORS
	 * @return
	 */
	public List<Doctor2> getAllDoctors() {
		return doctorRepository.getAllDoctors();
	}

	
	/**
	 * GET DOCTORS BY CATEGORY ID
	 * @param categoryId
	 * @return
	 */
	public List<Doctor1> getDoctorsByCategory(int categoryId) {
		// no need to throw exception when categoryId is not found because it will return empty []
		return doctorRepository.getDoctorsByCategory(categoryId);
	}
	
	
	/**
	 * GET CATEGROY IDS AND DOCTOR NAMES USING DOCTOR ID 
	 * @param doctorId
	 * @return
	 */
	public Doctor3 getCategoryAndNameByDoctorId(int doctorId) {
		doctorRepository.findById(doctorId)
				.orElseThrow(
						() -> new DoctorNotFoundException("id=" + doctorId + " cannot be found."));
		return doctorRepository.getCategoryAndNameByDoctorId(doctorId);
	}

	
	/**
	 * Converts String password to BCrypted value
	 * @param eg: "password"
	 * @return eg: "$2a$10$aRwVeemUfr2bzos2G6cjeOi0VfMc8NWu9ckS7XAzgRlzh5PKDEcaK"
	 */
	public String getPasswordEncoder(String password) {
    	String BCryptedValue = new BCryptPasswordEncoder().encode(password);
    	LOG.info("BCryptedValue of " + password + " = " + BCryptedValue);
		return BCryptedValue;
	}
	
}
