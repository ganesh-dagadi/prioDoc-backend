package com.gani_labs.com.PrioDoc.utils;
import java.util.UUID;
public interface CacheIntr {
	public void saveOTP(Integer OTP , String id , Integer seconds);
	public void saveOTP(Integer OTP , String id);
	public Integer getOTP(String id);
	public boolean OTPExists(String id);
}
