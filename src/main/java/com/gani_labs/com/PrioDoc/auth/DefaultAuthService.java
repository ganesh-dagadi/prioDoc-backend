package com.gani_labs.com.PrioDoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.gani_labs.com.PrioDoc.utils.Communications;
import com.gani_labs.com.PrioDoc.utils.MailFormat;

@Service
public class DefaultAuthService implements AuthService{
	@Autowired
	private UserRepository repo;
	@Autowired
	private Communications comm;
	@Override
	public String hashPassword(String password) {
		String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
		return pw_hash;
	}
	
	@Override
	public Person createPerson(Person person) {
		try {
			Person personSaved = repo.save(person);
			return personSaved;
		}catch(Exception err) {
			return null;
		}
	}
	
	@Override
	public boolean emailVerificationSendOTP(String email) {
		MailFormat mail = new MailFormat();
		mail.setTo(email);
		mail.setBody("Hello");
		mail.setSubject("testing");
		return comm.sendMail(mail);
	}
	
}
