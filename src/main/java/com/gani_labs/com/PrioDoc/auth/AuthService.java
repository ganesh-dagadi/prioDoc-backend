package com.gani_labs.com.PrioDoc.auth;

import java.util.AbstractMap;

import com.gani_labs.com.PrioDoc.auth.DAO.LoginRequest;

public interface AuthService {
	public String hashPassword(String passeord);
	public Person createPerson(Person person);
	public boolean emailVerificationSendOTP(String email);
	public String validateRegister(Person person);
	public AbstractMap.SimpleEntry<Boolean, String> validateOTP(String email , Integer otp);
	public Boolean resendOTP(String email);
	public String validateLogin(String email , String password);
	public AbstractMap.SimpleEntry<String, String> loginUser(String email , String password);
}
