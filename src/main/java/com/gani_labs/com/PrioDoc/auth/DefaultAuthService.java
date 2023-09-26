package com.gani_labs.com.PrioDoc.auth;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.gani_labs.com.PrioDoc.utils.CacheIntr;
import com.gani_labs.com.PrioDoc.utils.Communications;
import com.gani_labs.com.PrioDoc.utils.MailFormat;

@Service
public class DefaultAuthService implements AuthService{
	@Autowired
	private UserRepository repo;
	@Autowired
	private Communications comm;
	
	@Autowired
	private CacheIntr cache;
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
		Random rand = new Random();
		Integer otp = rand.nextInt(10000 , 99999);
		cache.saveOTP(otp , email , 300);
		mail.setTo(email);
		mail.setBody("Your OTP to verify your account is : " + otp.toString());
		mail.setSubject("Verify your account");
		return comm.sendMail(mail);
	}
	
	@Override
	public String validateRegister(Person person) {
		if(person.getEmail() == null) return "Email missing";
		if(person.getPassword() == null) return "password missing";
		if(person.getUsername() == null) return "username missing";
		Person foundPerson = repo.findByEmail(person.getEmail());
		if(foundPerson != null) return "Email is already taken";
		return null;
	}
	
}
