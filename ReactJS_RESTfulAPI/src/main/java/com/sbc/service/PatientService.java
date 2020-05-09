package com.sbc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbc.converter.User1;
import com.sbc.entity.Address;
import com.sbc.entity.Authority;
import com.sbc.entity.Patient;
import com.sbc.entity.User;
import com.sbc.enums.GenderEnum;
import com.sbc.enums.InsuranceEnum;
import com.sbc.enums.RoleEnum;
import com.sbc.enums.StateEnum;
import com.sbc.exception.DoctorNotFoundException;
import com.sbc.exception.DuplicateUsernameException;
import com.sbc.exception.PatientNotFoundException;
import com.sbc.projection.Patient1;
import com.sbc.projection.Patient4;
import com.sbc.repository.AddressRepository;
import com.sbc.repository.AuthorityRepository;
import com.sbc.repository.PatientRepository;
import com.sbc.repository.UserRepository;

@Service	
@Transactional
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);
	
	/**
	 *  SAVE PATIENT
	 * @param User1
	 * @return Doctor2
	 * First save Address, second save Patient(enable User) and third save Authority. 
	 * User.table (id, firstname, lastname, email, phone, gender, birthdate, username, password, addressid_fk) 
	 * Patient.table (id, insuranceid_fk)
	 * You don't have to create User object to save to User.table. You can create Patient object and
	 * override setter-getter methods of User.class to set values. When you save Patient object, records 
	 * will be saved in both User.table and Patient.table
	 **/
	public Patient savePatient(User1 user1) {
		
		
		// check whether username exists already before creating new User
		User user = userRepository.findByUsername(user1.getUsername());
		if(user != null)
			throw new DuplicateUsernameException("username already exist");
		
		Address address = new Address();
		address.setStreet(user1.getStreet());
		address.setCity(user1.getCity());
		address.setState(StateEnum.getEnum(user1.getState())); 						// convert String to Integer and call getEnum() of StateEnum
		address.setZipcode(user1.getZipcode());
		Address savedAddress = addressRepository.save(address);		// 1. SAVES ADDRESS TO ADDRESS.TABLE	

		Patient patient = new Patient();
		patient.setFirstname(user1.getFirstname());
		patient.setLastname(user1.getLastname());
		patient.setEmail(user1.getEmail());
		patient.setPhone(user1.getPhone());
		patient.setGender(GenderEnum.getEnum(user1.getGender())); 					// convert String to Integer and to Enum by calling getEnum()
		patient.setBirthdate(user1.getBirthdate());
		patient.setUsername(user1.getUsername());
		patient.setPassword(getPasswordEncoder(user1.getPassword()));
		patient.setAddress(savedAddress);
		patient.setInsurance(InsuranceEnum.getEnum(0));
		patient.setEnabled(true); // enable User
		Patient savedPatient = patientRepository.save(patient);		// 2. SAVES BOTH USER AND PATIENT TO USER.TABLE AND DOCTOR.TABLE
	
		Authority authority = new Authority(RoleEnum.ROLE_PATIENT, savedPatient);
		authorityRepository.save(authority);						// 3. SAVES AUTHORITY TO AUTHORITY.TABLE
		
		return savedPatient;		
	}

	
	/**
	 *  FIND DOCTOR BY ID - used to create _links
	 * @param id
	 * @return Patient
	 */
	public Patient getOnly(int id) {
		return patientRepository.findById(id)
				.orElseThrow(
						() -> new PatientNotFoundException("id=" + id + " cannot be found."));
	}

	
	/**
	 *  FIND PATIENT BY ID - used to retrieve patient data for updating purpose
	 * @param int id
	 * @return Patient4
	 */
	public Patient4 getPatientById(int id) {
		patientRepository.findById(id)
				.orElseThrow(
						() -> new PatientNotFoundException("id=" + id + " cannot be found."));
		Patient4 patient = patientRepository.findPatientById(id);
		return patient;
	}
	
	
	/**
	 *  UPDAYE PATIENT BY ID
	 * @param User1 user1
	 * @param id
	 * @return User1
	 * /**
	 * @author suny4 at 4/10/2020
	 * Because have not defined CascadeTypes [ALL, PERSIST, DELETE etc] between Patient-Address
	 * one-to-one mapping, we therefore have to retrieve the Address from the database and persist the values to
	 * Address.table. Had we define CascadeType.PERSIST, we could have persisted values in Address.table
	 * by using setAddress() method of Patient.class
	 */
	public User1 updatePatient(User1 user1, int id) {
		Patient _patient = patientRepository.findById(user1.getId())
				.orElseThrow(() -> new PatientNotFoundException("id=" + id + " cannot be found."));
		
		_patient.setFirstname(user1.getFirstname());
		_patient.setLastname(user1.getLastname());
		_patient.setEmail(user1.getEmail());
		_patient.setPhone(user1.getPhone());
		_patient.setGender(GenderEnum.getEnum(user1.getGender())); 	// convert String to Integer and to Enum by calling getEnum() 
		_patient.setBirthdate(user1.getBirthdate());
		_patient.setUsername(user1.getUsername());
		_patient.setPassword(user1.getPassword());
		/*
		 * Since CascadeType is not CascadeType.ALL, we have to save Address object but we can 
		 * persist the doctor object. If you try to LOG doctor.toString(), it will create a large 
		 * iteration of objects and crash the server. So, avoid returning Doctor Object or priting it.
		 */
		Address address = addressRepository.findById(_patient.getAddress().getId())
				.orElseThrow(() -> new DoctorNotFoundException("id=" + id + " cannot be found."));	
		
		address.setStreet(user1.getStreet());
		address.setCity(user1.getCity());
		address.setState(StateEnum.getEnum(user1.getState())); // convert String to Integer and to Enum using getEnum()
		address.setZipcode(user1.getZipcode());
		 
		return user1;	// return User1 instead of Patient because returning Patient type will create infinite loops because of circular-mapping 
	}

	
	/**
	 *  LIST ALL PATIENTS
	 * @return
	 */
	public List<Patient1> getAllPatients() {
		return patientRepository.getAllPatients();
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
