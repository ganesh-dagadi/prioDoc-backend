package com.gani_labs.com.PrioDoc.utils;

import java.util.Map;

public interface CryptoIntr {
	public String generateJWT(String payload_id , String payload , Boolean isAccess , Boolean willExpire , Integer expiresIn);
	public void decodeJWT(String token , Boolean isAccess);
}
