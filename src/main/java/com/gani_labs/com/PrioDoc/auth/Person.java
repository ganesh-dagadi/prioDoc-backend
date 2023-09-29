package com.gani_labs.com.PrioDoc.auth;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Person {
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="person_id", insertable = false, updatable = false, nullable = false)
	private UUID person_id;
	@Column(name="username", nullable = false , unique=true)
	private String username;
	@Column(name="email", nullable = false , unique = true)
	private String email;
	@Column(name="password",nullable = false)
	private String password;
	@Column(name="isActive",nullable = false)
	private boolean isActive = false;
	@Column(name="isVerfied",nullable = false)
	private boolean isVerified = false;
	
	@OneToMany(mappedBy = "person_id")
	private List<RefreshToken> refreshToken;
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public UUID getPerson_id() {
		return person_id;
	}
	public void setPerson_id(UUID person_id) {
		this.person_id = person_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
