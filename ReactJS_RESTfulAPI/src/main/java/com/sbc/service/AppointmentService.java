package com.sbc.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbc.entity.Appointment;
import com.sbc.entity.AppointmentDetail;
import com.sbc.enums.StatusEnum;
import com.sbc.exception.AppointmentNotFoundException;
import com.sbc.exception.ForbiddenException;
import com.sbc.projection.Appointment1;
import com.sbc.projection.Appointment2;
import com.sbc.projection.Appointment3;
import com.sbc.projection.Appointment4;
import com.sbc.repository.AppointmentDetailRepository;
import com.sbc.repository.AppointmentRepository;

@Service	
@Transactional
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
 
	@Autowired
	private AppointmentDetailRepository appointmentDetailRepository;

	private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);
	
	/* SAVE APPOINTMENT */
	public Appointment save(Appointment appointment) {
		appointment.setStatus(StatusEnum.BOOKED);			// create status BOOKED when patient books appointment 
		
		AppointmentDetail appointmentDetail = new AppointmentDetail();
		AppointmentDetail appDetail = appointmentDetailRepository.save(appointmentDetail);
		appointment.setAppointmentdetail(appDetail);
		return appointmentRepository.save(appointment);
	}

	/* GET-ALL APPOINTMENTS */
	public List<Appointment1> getAll() {
		return appointmentRepository.getAllAppointments();
	}

	/* GET-ALL APPOINTMENTS AFTER THE SELECTED DATE */
	public List<Appointment4> getAppointmentsAfterThisDate(Date date) {
		return appointmentRepository.getAppointmentsAfterThisDate(date);
	}
	
	/* GET APPOINTMENT BY ID */
	public Appointment getOnly(int id) {
		return appointmentRepository.findById(id)
				.orElseThrow(
						() -> new AppointmentNotFoundException("id=" + id + " cannot be found."));
	}

	/* UPDATE APPOINTMENT */
	public List<Appointment1> update(Appointment appointment, int id) {
		Appointment appt = appointmentRepository.findById(id)
						.orElseThrow(() -> new AppointmentNotFoundException("id=" + id + " cannot be found."));
		appt.setStarttime(appointment.getStarttime());
		appt.setAppointmentdetail(appointment.getAppointmentdetail());		
		return getAll();
	}

	/* DELETE APPOINTMENT */
	public List<Appointment1> delete(int id) {
		appointmentRepository.findById(id)
					.orElseThrow(() -> new AppointmentNotFoundException("id=" + id + " cannot be found."));
		appointmentRepository.deleteById(id);
		return getAll();
	}
	
	
	/* GET APPOINTMENTS USING DOCTORID AFTER A CERTAIN DATE (SELECTED DATE) */
	public List<Appointment2> getAppointmentsByDoctorAfterSelectedDateTime(int doctorId, Date selectedDateTime) {
		return appointmentRepository.getAppointmentsByDoctorAfterSelectedDateTime(doctorId, selectedDateTime);
	}
	 
	
	/* GET APPOINTMENTS BY DOCTORID ON A SELECTED DATE */
	public List<Appointment2> getAppointmentsByDoctorOnSelectedDate(int doctorId, String selectedDateTime) {
		return appointmentRepository.getAppointmentsByDoctorOnSelectedDate(doctorId, selectedDateTime);
	}
	
	
	/* GET APPOINTMENTS BY PATIENT ID */
	public List<Appointment3> getAppointmentsByPatientId(int patientId) {
		return appointmentRepository.getAppointmentsByPatientId(patientId);
	}
	
	
	/* GET APPOINTMENTS BY PATIENT ID AND STATUS */
	public List<Appointment3> getAppointmentsByPatientIdAndStatus(int patientId, int status) {
		return appointmentRepository.getAppointmentsByPatientIdAndStatus(patientId, status);
	}
	
	
	/* DELETE APPOINTMENT AND APPOINTMENT_DETAIL USING APPOINTMENTID - BY ADMIN */
	public void deleteAppointmentAndAppointmentDetailUsingAppointmentIdByAdmin(int appointmentId) {
		LOG.info("appointmentId=" + appointmentId);
		Appointment appointment = appointmentRepository.findById(appointmentId)
									.orElseThrow(
											() -> new AppointmentNotFoundException("id=" + appointmentId + " cannot be found."));
		
		LOG.info("appointment.getAppointmentdetail()=" + appointment.getAppointmentdetail().getId());
		// delete only if status is booked
		if(appointment.getStatus() == StatusEnum.BOOKED) {
			LOG.info("appointment.getAppointmentdetail()=" + appointment.getAppointmentdetail().getId());
			appointmentRepository.deleteById(appointment.getId());				// first delete from appointment table
			appointmentDetailRepository.deleteById(appointment.getAppointmentdetail().getId());		// second delete from appointment_detail table
		}
		else {
			throw new ForbiddenException("Appointment with status=BOOKED can only be deleted.");
		}
		
	}

	
	/* DELETE APPOINTMENT AND APPOINTMENT_DETAIL USING APPOINTMENTID - BY PATIENT */
	public void deleteAppointmentAndAppointmentDetailUsingAppointmentIdByPatient(int appointmentId) {
		LOG.info("appointmentId=" + appointmentId);
		Appointment appointment = appointmentRepository.findById(appointmentId)
									.orElseThrow(
											() -> new AppointmentNotFoundException("id=" + appointmentId + " cannot be found."));
		
		LOG.info("appointment.getAppointmentdetail()=" + appointment.getAppointmentdetail().getId());
		// delete only if status is booked
		if(appointment.getStatus() == StatusEnum.BOOKED) {
			
			// derive localtime based on system time
			LOG.info("nowLocalTime=" + new Date());
			
			// derive UTC from new Date()
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			LOG.info("UTC new Date()=" + dateFormat.format(date));
			
			// derive UTC from Instant
			Instant _nowUTC = Instant.now();
			LOG.info("_nowUTC=" + _nowUTC);                       // UTC time 2020-05-08T12:38:25.637Z
			
			// derive UNIX time from Instant
			long nowUNIX = Instant.now().getEpochSecond();        // 1588940413 seconds
			LOG.info("nowUNIX=" + nowUNIX);

			// derive UNIX time from UTC Date
			long starttimeUNIX = appointment.getStarttime().getTime() / 1000;        // 1589041800 seconds
			LOG.info("starttimeUNIX=" + starttimeUNIX);
			
			// find difference in dates
			long diffSeconds = starttimeUNIX - nowUNIX;      	            
	        LOG.info("diffSeconds=" + diffSeconds);
	        if(diffSeconds >= 172800) {                  // 48 hours = 2880 minutes = 172800 seconds
	        	appointmentRepository.deleteById(appointment.getId());				// first delete from appointment table
				appointmentDetailRepository.deleteById(appointment.getAppointmentdetail().getId());		// second delete from appointment_detail table
	        } else {
	        	throw new ForbiddenException("Appointment cannot be due in less than 48 hours.");
	        }
		}
		else {
			throw new ForbiddenException("Appointment with status=BOOKED can only be deleted.");
		}
		
	}
	
	
	/* UPDATE STATUS FROM BOOKED TO RECEIVED */
	public Appointment updateStatus(int id) {
		Appointment foundAppointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("id=" + id + " cannot be found."));
		foundAppointment.setStatus(StatusEnum.RECEIVED);
		return foundAppointment;
	}
}
