package com.sbc.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbc.controller.AppointmentDetailController;
import com.sbc.converter.AppointmentDetail2;
import com.sbc.entity.Appointment;
import com.sbc.entity.AppointmentDetail;
import com.sbc.enums.StatusEnum;
import com.sbc.exception.AppointmentDetailNotFoundException;
import com.sbc.exception.AppointmentNotFoundException;
import com.sbc.projection.AppointmentDetail1;
import com.sbc.repository.AppointmentDetailRepository;
import com.sbc.repository.AppointmentRepository;

@Service	
@Transactional
public class AppointmentDetailService {
	
	@Autowired
	private AppointmentDetailRepository appointmentDetailRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(AppointmentDetailService.class);
	
	/**
	 *  UPDATE APPOINTMENTDETAIL BY APPOINTMENTID
	 * @param Id, AppointmentDetail
	 * @return AppointmentDetail
	 */
	public AppointmentDetail updateAppointmentdetail(AppointmentDetail2 appointmentDetail) {
		
		LOG.info("AppointmentDetail = " + appointmentDetail.toString());
		
		// update AppointmentDetail 
		AppointmentDetail appDetail = appointmentDetailRepository.findById(appointmentDetail.getAppointmentDetailId())
				.orElseThrow(
						() -> new AppointmentDetailNotFoundException("id=" + appointmentDetail.getAppointmentDetailId() + " cannot be found."));
		appDetail.setReason(appointmentDetail.getReason());
		appDetail.setPrescription(appointmentDetail.getPrescription());
		appDetail.setNote(appointmentDetail.getNote());
		appDetail.setTreatment(appointmentDetail.getTreatment());
		
		// if StatusEnum.RECEIVED then update to StatusEnum.COMPLETED 
		if(StatusEnum.getEnum(appointmentDetail.getAppointmentStatus()).name() == "RECEIVED") {
			Appointment appointment = appointmentRepository.findById(appointmentDetail.getAppointmentId())
			.orElseThrow(
					() -> new AppointmentNotFoundException("id=" + appointmentDetail.getAppointmentId() + " cannot be found."));
			appointment.setStatus(StatusEnum.COMPLETED);
		}
		
		return appDetail;
	}
	
	
	/**
	 *  FIND APPOINTMENTDETAIL USING APPOINTMENTID
	 * @param appointment Id
	 * @return AppointmentDetail1
	 */
	public AppointmentDetail1 getAppointmentDetailByAppointmentId(int appointmentId) {		
		appointmentRepository.findById(appointmentId)					// check whether appointment Id exist; if does not exist throw exception
			.orElseThrow(
					() -> new AppointmentNotFoundException("id=" + appointmentId + " cannot be found."));
		
		return appointmentDetailRepository.getAppointmentDetailByAppointmentId(appointmentId);
	}
	
	
}
