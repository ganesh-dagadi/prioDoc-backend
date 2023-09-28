package com.gani_labs.com.PrioDoc.utils;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class CryptoImpl implements CryptoIntr {
	//Default implementation of crypto interface using AUTH0
	@Value("${java.crypto.jwt_access_secret}")
	String JWT_ACCESS_SECRET;
	@Value("${java.crypto.jwt_refresh_secret}")
	String JWT_REFRESH_SECRET;
	
	@Override
	public String generateJWT(String payload_id , String payload , Boolean isAccess, Boolean willExpire , Integer expiresIn) {
		try {
			Algorithm jwtAlgo;
			if(isAccess) {
				jwtAlgo = Algorithm.HMAC256(JWT_ACCESS_SECRET);
			}else {
				jwtAlgo = Algorithm.HMAC256(JWT_REFRESH_SECRET);
			}
			Instant expires = Instant.now();
			expires.plusSeconds(expiresIn);
			
			String token;
			if(willExpire) {
				token = JWT.create()
				.withClaim(payload_id, payload)
				.withExpiresAt(expires)
				.withIssuedAt(Instant.now())
				.sign(jwtAlgo);
			}else {
				token = JWT.create()
				.withClaim(payload_id, payload)
				.withIssuedAt(Instant.now())
				.sign(jwtAlgo);
			}
			return token;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public void decodeJWT(String token , Boolean isAccess){
		
	}
}
