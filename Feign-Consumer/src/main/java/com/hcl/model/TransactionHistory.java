package com.hcl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class TransactionHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	private long fromAccountNumber;
	private long toAccountNumber;
	private double transactionAmount;
	private Date transactionDate;
	private String transctionStatus;

	public TransactionHistory(long fromAccountNumber, long toAccountNumber, double transactionAmount,
			Date transactionDate, String transctionStatus) {
		super();
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transctionStatus = transctionStatus;
	}

}
