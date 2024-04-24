package com.hcl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	public Optional<Account> findByAccountNumber(long accountNumber);

}
