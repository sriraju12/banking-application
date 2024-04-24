package com.hcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.TransactionHistory;

@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, Long> {

}
