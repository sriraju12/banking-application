package com.hcl.capstone.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hcl.capstone.customexception.EmptyDataException;
import com.hcl.capstone.entity.Customer;
import com.hcl.capstone.entity.TransactionAudit;
import com.hcl.capstone.repository.CustomerRepository;
import com.hcl.capstone.repository.TransactionAuditRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionAuditRepository transactionAuditRepository;

	Map<String, String> result = new HashMap<String, String>();

	public Customer create(Customer customer) {
		return customerRepository.save(customer);

	}

	public List<Customer> getAll() {
		List<Customer> customers = customerRepository.findAll();
		if (customers.isEmpty())
			throw new EmptyDataException("No data found in database");
		else
			return customers;
	}

	public ResponseEntity<?> getOneById(int id) {

		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			TransactionAudit transactionAudit = new TransactionAudit(0, id, new Date(), "sucess", "", new Date());
			transactionAuditRepository.save(transactionAudit);
			return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
		}

		else {

			TransactionAudit transactionAudit = new TransactionAudit(0, id, new Date(), "failure",
					"invalid customer id", new Date());
			transactionAuditRepository.save(transactionAudit);
			result.put("CustomerId", "" + id);
			result.put("statusCode", "899");
			result.put("Failure reason", "invalid customer Id");
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

		}

	}

	public void delete(int id) {
		customerRepository.deleteById(id);
	}

	public Customer update(Customer customer) {
		return customerRepository.save(customer);
	}

}
