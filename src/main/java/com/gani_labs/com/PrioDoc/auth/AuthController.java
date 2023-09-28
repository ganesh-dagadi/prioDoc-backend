package com.gani_labs.com.PrioDoc.auth;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gani_labs.com.PrioDoc.LoginRequest;
import com.gani_labs.com.PrioDoc.auth.DAO.ResendOTPRequest;
import com.gani_labs.com.PrioDoc.auth.DAO.ValidateOTPRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody Person person) {
		String validationFailedMsg = service.validateRegister(person);
		
		if(validationFailedMsg != null) {
			System.out.print(validationFailedMsg);
			BodyBuilder response= ResponseEntity.status(409);
			Map<String , Object> m = new HashMap<>();
			m.put("err", validationFailedMsg);
			return response.body(m);
		}
		person.setPassword(service.hashPassword(person.getPassword()));
		Person newPerson = service.createPerson(person);
		service.emailVerificationSendOTP(newPerson.getEmail());
		Map<String , Object> response = new HashMap<>();
		response.put("msg" , "Enter OTP sent by mail");
		response.put("Person", newPerson);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/validateotp")
	public ResponseEntity<String> validateOTP(@RequestBody ValidateOTPRequest valReq){
		AbstractMap.SimpleEntry<Boolean, String> serviceResponse = service.validateOTP(valReq.getEmail() , valReq.getOTP());
		System.out.print(serviceResponse.getValue());
		if(serviceResponse.getKey() == true) {
			return ResponseEntity.ok("Account verified");
		}else {
			return ResponseEntity.status(400).body(serviceResponse.getValue());
		}
	}
	
	@PatchMapping("/resendotp")
	public ResponseEntity<String> resendOTP(@RequestBody ResendOTPRequest resendReq){
		if(service.resendOTP(resendReq.getEmail()) == true) return ResponseEntity.ok("OTP has been sent");
		else return ResponseEntity.status(500).body("Unable to send OTP. Try again later");
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String , Object>>login(@RequestBody LoginRequest loginReq){
		String validationFailedMsg = service.validateLogin(loginReq.getEmail() , loginReq.getPassword());
		if(validationFailedMsg != null) {
			System.out.print(validationFailedMsg);
			BodyBuilder response= ResponseEntity.status(409);
			Map<String , Object> m = new HashMap<>();
			m.put("err", validationFailedMsg);
			return response.body(m);
		}
		AbstractMap.SimpleEntry<String, String> tokens = service.loginUser(loginReq.getEmail() , loginReq.getPassword());
		Map<String , Object> m = new HashMap<>();
		m.put("tokens", tokens);
		return ResponseEntity.ok(m);
	}
}
