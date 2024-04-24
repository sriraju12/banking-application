package com.hcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
