package com.gani_labs.com.PrioDoc.utils;


import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPooled;

@Component
public class CacheImpl implements CacheIntr {
	
	JedisPooled jedis = new JedisPooled("localhost", 6379);
	//with expiration
	@Override
	public void saveOTP(Integer OTP, String id , Integer seconds) {
		String key = "OTP_"+ id;
		jedis.set(key, OTP.toString());
		jedis.expire(key, seconds);
	}
	
	public void saveOTP(Integer OTP, String id) {
		String key = "OTP_"+ id;
		jedis.set(key, OTP.toString());
	}

	@Override
	public Integer getOTP(String id) {
		String key = "OTP_"+id;
		if(!this.OTPExists(id)) return -1;
		return Integer.parseInt(jedis.get(key));
	}

	@Override
	public boolean OTPExists(String id) {
		String key = "OTP_"+id;
		return jedis.exists(key);
	}

}
