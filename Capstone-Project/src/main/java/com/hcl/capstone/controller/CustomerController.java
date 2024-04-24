package com.hcl.capstone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.capstone.customexception.NoSuchIdPresentInDBException;
import com.hcl.capstone.entity.Customer;
import com.hcl.capstone.repository.CustomerRepository;
import com.hcl.capstone.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerRepository customerRepository;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.create(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.ACCEPTED);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> allCustomers = customerService.getAll();
		return new ResponseEntity<List<Customer>>(allCustomers, HttpStatus.FOUND);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneById(@PathVariable("id") int id) {
		return new ResponseEntity<>(customerService.getOneById(id), HttpStatus.FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {

		Optional<Customer> optCustomer = customerRepository.findById(id);

		if (optCustomer.isPresent()) {
			customerService.delete(id);
			return new ResponseEntity<>("deleted sucessfully", HttpStatus.ACCEPTED);
		} else {
			 throw new NoSuchIdPresentInDBException("id" + id + " is not present in database");
			//return new ResponseEntity<>("id is not present in database", HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Customer customer) {

		Optional<Customer> optCustomer = customerRepository.findById(id);

		if (optCustomer.isPresent())
			return new ResponseEntity<>(customerService.update(customer), HttpStatus.ACCEPTED);
		else
			throw new NoSuchIdPresentInDBException("id" + id + " is not present in database");

	}

}
