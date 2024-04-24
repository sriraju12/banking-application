package com.hcl.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.customexception.IdIsnotPresentException;
import com.hcl.model.Account;
import com.hcl.model.Customer;
import com.hcl.repository.CustomerRepo;
import com.hcl.service.AccountService;
import com.hcl.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	AccountService accountService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerRepo customerRepo;

	@PostMapping("/addcustomer")
	public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
		Customer sendCustomer = customerService.addCustomer(customer);
		return new ResponseEntity<>(sendCustomer, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getallcustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> getAll = customerService.getCustomers();

		return new ResponseEntity<List<Customer>>(getAll, HttpStatus.FOUND);
	}

	@DeleteMapping("deletecustomer")
	public ResponseEntity<?> deleteCustomerById(long customerId) {
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>("customer deleted sucessfully", HttpStatus.ACCEPTED);
	}

	@PutMapping("/fundtransfer")
	public ResponseEntity<?> transferAmount(@RequestParam long source, @RequestParam long destination,
			@RequestParam long amount) {
		return customerService.fundTransfer(source, destination, amount);
	}

	@GetMapping("/getallaccounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.getAccounts();
		return new ResponseEntity<List<Account>>(accounts, HttpStatus.FOUND);
	}

	@DeleteMapping("/deleteaccount")
	public ResponseEntity<?> deleteAccounts(long accountId) {
		accountService.deleteAccount(accountId);
		return new ResponseEntity<>("Account deleted sucessfully", HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		Optional<Customer> optCustomer = customerRepo.findById(id);
		if (optCustomer.isPresent())

			return new ResponseEntity<>(customerService.editCustomer(customer), HttpStatus.ACCEPTED);

		else
			throw new IdIsnotPresentException("Id is not present in Database");

	}

}
