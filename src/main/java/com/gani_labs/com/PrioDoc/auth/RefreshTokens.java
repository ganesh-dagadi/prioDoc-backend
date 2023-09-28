package com.gani_labs.com.PrioDoc.auth;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class RefreshTokens {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="token_id", insertable = false, updatable = false, nullable = false)
	private UUID token_id;
	
	@ManyToOne
	@PrimaryKeyJoinColumn()
	private Person person_id;
	
	@Column(name="token", updatable = false, nullable = false)
	private String token;
	public UUID getToken_id() {
		return token_id;
	}
	public void setToken_id(UUID token_id) {
		this.token_id = token_id;
	}
	public Person getPerson_id() {
		return person_id;
	}
	public void setPerson_id(Person person_id) {
		this.person_id = person_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
