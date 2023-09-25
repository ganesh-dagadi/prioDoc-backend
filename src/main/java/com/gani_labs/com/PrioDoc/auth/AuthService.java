package com.gani_labs.com.PrioDoc.auth;

public interface AuthService {
	public String hashPassword(String passeord);
	public Person createPerson(Person person);
	public boolean emailVerificationSendOTP(String email);
}
