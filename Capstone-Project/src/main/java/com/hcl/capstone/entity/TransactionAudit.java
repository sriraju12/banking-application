package com.hcl.capstone.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TransactionAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int iMessage;
	private Date messageInTime;
	private String status;
	private String reasonCodes;
	private Date messageOutTime;

}
