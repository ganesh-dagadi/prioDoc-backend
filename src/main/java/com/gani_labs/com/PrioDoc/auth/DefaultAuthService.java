package com.gani_labs.com.PrioDoc.auth;

import java.util.AbstractMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.gani_labs.com.PrioDoc.utils.CacheIntr;
import com.gani_labs.com.PrioDoc.utils.Communications;
import com.gani_labs.com.PrioDoc.utils.CryptoIntr;
import com.gani_labs.com.PrioDoc.utils.MailFormat;


@Service
public class DefaultAuthService implements AuthService{
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private RefreshTokenRepository refreshRepo;
	@Autowired
	private Communications comm;
	
	@Autowired
	private CacheIntr cache;
	@Autowired
	private CryptoIntr crypto;
	@Override
	public String hashPassword(String password) {
		String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
		return pw_hash;
	}
	
	@Override
	public Person createPerson(Person person) {
		try {
			Person personSaved = userRepo.save(person);
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
		Person foundPerson = userRepo.findByEmail(person.getEmail());
		if(foundPerson != null) return "Email is already taken";
		return null;
	}
	
	@Override 
	public AbstractMap.SimpleEntry<Boolean, String> validateOTP(String email , Integer otp){
		Integer foundOTP = cache.getOTP(email);
		if(foundOTP == -1) return new AbstractMap.SimpleEntry<Boolean , String>(false , "OTP might have expired");
		System.out.println(foundOTP  + " " + otp);
		if(foundOTP.equals(otp)) {
			Person person = userRepo.findByEmail(email);
			person.setActive(true);
			person.setVerified(true);
			userRepo.save(person);
			return new AbstractMap.SimpleEntry<Boolean , String>(true , "Account has been verified");
		}
		else return new AbstractMap.SimpleEntry<Boolean , String>(false , "OTP is incorrect");
	}
	
	@Override
	public Boolean resendOTP(String email){
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
	public String validateLogin(String email , String password) {
		if(email == null || password == null) return "Required parameters missing";
		Person person = userRepo.findByEmail(email);
		if(person == null) return "User with email not found";
		return null;
	}
	
	@Override
	public AbstractMap.SimpleEntry<String, String> loginUser(String email , String password){
		Person person = userRepo.findByEmail(email);
		if(!BCrypt.checkpw(password , person.getPassword())){
			return null;
		}
		String accessToken = crypto.generateJWT("user_id" , person.getPerson_id().toString(),true , true , 1200);
		String refreshToken = crypto.generateJWT("user_id", person.getPerson_id().toString(), false , false , 0);
		RefreshTokens refreshInst = new RefreshTokens();
		refreshInst.setPerson_id(person);
		refreshInst.setToken(refreshToken);
		refreshRepo.save(refreshInst);
		return new AbstractMap.SimpleEntry<String, String>(accessToken , refreshToken);
	}
	
}
