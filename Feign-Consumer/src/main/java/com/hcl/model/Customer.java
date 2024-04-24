package com.hcl.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customerId")
	private Long customerId;
	private String customerName;
	@Temporal(TemporalType.DATE)
	private Date customerDOB;
	private String customerGender;
	private String customerAadhar;
	private String customerPan;
	private String customerEmail;
	private String customerContact;
	private String customerAddress;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	@JsonIgnoreProperties("customer")
	List<Account> account;

}
