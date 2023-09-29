package com.gani_labs.com.PrioDoc.utils;

import com.gani_labs.com.PrioDoc.auth.Person;

public interface MiddlewareIntr {
	public Person authenticateRequest(String token);
}
