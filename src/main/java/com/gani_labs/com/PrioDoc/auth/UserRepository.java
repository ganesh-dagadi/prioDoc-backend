package com.gani_labs.com.PrioDoc.auth;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface UserRepository extends JpaRepository<Person , Integer>{
	public Person findByEmail(String email);
}
