package com.gani_labs.com.PrioDoc.auth.DAO;

public class ValidateOTPRequest {
	String email;
	Integer OTP;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOTP() {
		return OTP;
	}
	public void setOTP(Integer oTP) {
		OTP = oTP;
	}
}
