package com.gani_labs.com.PrioDoc.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

//Default communication class uses Twilio sendgrid for mail
@Component
public class DefaultCommunication implements Communications {
	@Value("${java.sendgrid_api_key}")
	private String SENDGRID_API_KEY;
	@Override
	public boolean sendMail(MailFormat mail) {
		Email from = new Email("ganeshdagadi3@gmail.com");
	    String subject = mail.getSubject();
	    Email to = new Email(mail.getTo());
	    Content content = new Content("text/plain", mail.getBody());
	    Mail sendgridMail = new Mail(from, subject, to, content);
	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(sendgridMail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	      if(response.getStatusCode() == 200) return true;
	      else return false;
	    } catch (IOException ex) {
	      return false;
	    }
	}

}
