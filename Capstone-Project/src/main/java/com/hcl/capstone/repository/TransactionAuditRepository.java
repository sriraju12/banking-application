package com.hcl.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.capstone.entity.TransactionAudit;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Integer> {

}
