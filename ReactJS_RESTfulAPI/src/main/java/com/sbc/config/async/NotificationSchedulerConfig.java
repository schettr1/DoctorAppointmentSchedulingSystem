package com.sbc.config.async;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sbc.projection.Appointment4;
import com.sbc.service.AppointmentService;
import com.sbc.service.NotificationService;

@Component
@EnableScheduling
public class NotificationSchedulerConfig {

	@Autowired 
	private AppointmentService appointmentService;
	
	@Autowired
	private NotificationService notificationService;
	

	/**
	 * Scheduled method run on a separate thread
	 * every 24 hours.
	 * time in milliseconds (1 second=1000 milliseconds)
	 */
	@Scheduled(fixedRate = 24*60*60*1000)        
	public void scheduleBackgroundTask() {
		
		Calendar cal = Calendar.getInstance();
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   System.out.println("Today's date is "+dateFormat.format(cal.getTime()));		// 2020-05-10
		   // cal.add(Calendar.DATE, -90); // to add or substract days from the current date.
		   // System.out.println("After 90 days substracted "+dateFormat.format(cal.getTime())); 	// 2020-02-10
		   
		List<Appointment4> list = appointmentService.getAppointmentsAfterThisDate(cal.getTime());	
		System.out.println("List appointments due in two days");
		list.forEach(x -> System.out.println("[id=" + x.getId() + ", "
											+ "starttime=" + x.getStarttime() + ", "
											+ "endtime=" + x.getEndtime() + ", "
											+ "firstname=" + x.getFirstname()
											+ ", lastname=" + x.getLastname() 
											+ ", email=" + x.getEmail() + 
											"]"));	
		
		for(Appointment4 appointment: list) {
			notificationService.sendNotification(appointment);			
		}		
	}
	
	
	
}
