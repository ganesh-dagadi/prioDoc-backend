package com.gani_labs.com.PrioDoc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gani_labs.com.PrioDoc.auth.Person;
import com.gani_labs.com.PrioDoc.auth.UserRepository;

@Component
public class MiddlewareImpl implements MiddlewareIntr {
	
	@Autowired
	CryptoIntr crypto;
	
	@Autowired
	UserRepository personRepo; 
	@Override
	public Person authenticateRequest(String token) {
		System.out.println(token);
		String personId = crypto.decodeJWT(token, "user_id");
		return null;
	}

}
