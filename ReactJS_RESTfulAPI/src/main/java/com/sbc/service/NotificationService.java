package com.sbc.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sbc.exception.FailedEmailDeliveryException;
import com.sbc.projection.Appointment4;


@Component
public class NotificationService {

	@Autowired
	private JavaMailSender javaMailSender; 
	
	
	@Value("${spring.mail.username}")		
	private String custom_gmail;


	public void sendNotification(Appointment4 appointment) {
		
		try {
			/* MimeMessage - with Attachment */
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);		// set 'true' to change 'plain/text' to 'html/text'
			mimeMessageHelper.setFrom(custom_gmail);
			//mimeMessageHelper.setTo(appointment.getEmail());
			mimeMessageHelper.setTo(custom_gmail);
			mimeMessageHelper.setSubject("Appointment Remainder");
			String text = "<html>"
					+ "<p>" + appointment.getFirstname() + ",</p>" 
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;You have an appointment scheduled with Dr. XYZ on " + appointment.getStarttime() + " at our location 123 Riverdale Plaza, Towson MD 21211</p>" 
					+ "<p>If you would like to make changes to your appointment, please call our office at phone 443-333-4444.</p>" 
					+ "<p>We thank you for being our customer.</p>"
					+ "<div>Please do not reply to this email.</div>"
					+ "<p>Sincerely,</p>"
					+ "<p>Medical Associates</p>"
					+ "</html>";
			mimeMessageHelper.setText(text, true);		// set 'true' to change 'plain/text' to 'html/text'	
				
			javaMailSender.send(mimeMessage);
			
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch(FailedEmailDeliveryException e) {
			System.out.println("FailedEmailDeliveryException");
		}		
		
		System.out.println("Message sent!");
		
	}
	
    

}

