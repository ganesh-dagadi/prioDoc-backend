package com.gani_labs.com.PrioDoc.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokens , UUID> {
	
}
