package com.gani_labs.com.PrioDoc.utils;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

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
			expires = expires.plusSeconds(expiresIn);
			
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
	public String decodeJWT(String token , String key){
		Algorithm jwtAlgo = Algorithm.HMAC256(JWT_ACCESS_SECRET);
		try {
			DecodedJWT decoded = JWT.require(jwtAlgo).build().verify(token);
			System.out.println(decoded.getClaim(key));
			return decoded.getClaim(key).toString();
		}catch(TokenExpiredException e) {
			System.out.println("token expired");
			return null;
		}catch(SignatureVerificationException e) {
			System.out.println("Invalid signature");
			return null;
		}catch(Exception e) {
			System.out.println("SOmething else");
			return null;
		}
		
	}
}
