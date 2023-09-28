package com.gani_labs.com.PrioDoc.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class CryptoImpl implements CryptoIntr {
	//Default implementation of crypto interface using AUTH0
	@Value("${java.crypto.jwt_access_secret}")
	String JWT_ACCESS_SECRET;
	@Value("${java.crypto.jwt_refresh_secret}")
	String JWT_REFRESH_SECRET;
	
	@Override
	public String generateJWT(String payload_id , String payload , Boolean isAccess) {
		try {
			Algorithm jwtAlgo;
			if(isAccess) {
				jwtAlgo = Algorithm.HMAC256(JWT_ACCESS_SECRET);
			}else {
				jwtAlgo = Algorithm.HMAC256(JWT_REFRESH_SECRET);
			}
			String token = JWT.create()
					.withClaim(payload_id, payload)
					.sign(jwtAlgo);
			return token;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public void decodeJWT(String token , Boolean isAccess){
		
	}
}
