package com.gani_labs.com.PrioDoc.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
