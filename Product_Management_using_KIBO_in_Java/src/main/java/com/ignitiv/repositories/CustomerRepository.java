package com.ignitiv.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignitiv.entities.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
	
	Optional<CustomerEntity> findByEmail(String email);
	
	Optional<CustomerEntity> findByCustomerAccountId(String customerAccountId);
	
	Boolean existsByCustomerAccountId(Integer customerAccountId);
	
	Boolean existsByEmail(String email);
	
}
