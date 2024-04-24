package com.hcl.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hcl.customexception.EmptyDataFoundInDatabaseException;
import com.hcl.customexception.EmptyInputException;
import com.hcl.model.Account;
import com.hcl.model.Customer;
import com.hcl.model.TransactionHistory;
import com.hcl.repository.AccountRepo;
import com.hcl.repository.CustomerRepo;
import com.hcl.repository.TransactionHistoryRepo;

@Service
public class CustomerService {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	TransactionHistoryRepo historyRepo;

	@Autowired
	AccountRepo accountRepo;

	public Customer addCustomer(Customer customer) {
		if (customer.getCustomerName().isEmpty() || customer.getCustomerGender().isEmpty()
				|| customer.getCustomerAadhar().isEmpty() || customer.getCustomerPan().isEmpty()
				|| customer.getCustomerEmail().isEmpty() || customer.getCustomerContact().isEmpty()
				|| customer.getCustomerAddress().isEmpty())
			throw new EmptyInputException("Empty input fields");

		return customerRepo.save(customer);
	}

	public List<Customer> getCustomers() {
		List<Customer> allCustomers = customerRepo.findAll();
		if (allCustomers.isEmpty())
			throw new EmptyDataFoundInDatabaseException("No Data found in Database");

		return allCustomers;
	}

	public void deleteCustomer(Long customerId) {
		customerRepo.deleteById(customerId);
	}

	public ResponseEntity<?> fundTransfer(long source, long destination, double amount) {

		Map<String, String> msg = new HashMap<String, String>();
		if (source == destination) {
			msg.put("status", "Transaction Falied");
			msg.put("message", "Source and Destination should not be same");
			TransactionHistory th = new TransactionHistory(source, destination, amount, new Date(), "Failed");
			historyRepo.save(th);
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

		Optional<Account> srcAccount = accountRepo.findByAccountNumber(source);
		Optional<Account> destAccount = accountRepo.findByAccountNumber(destination);

		if (srcAccount.isPresent() && destAccount.isPresent()) {
			if (amount > 1) {
				Account getSrcAccount = srcAccount.get();
				Account getDestAccount = destAccount.get();
				if (getSrcAccount.getAccountBalance() >= amount) {
					getSrcAccount.setAccountBalance((getSrcAccount.getAccountBalance() - amount));
					accountRepo.save(getSrcAccount);
					getDestAccount.setAccountBalance((getDestAccount.getAccountBalance() + amount));
					accountRepo.save(getDestAccount);
					TransactionHistory th = new TransactionHistory(source, destination, amount, new Date(), "Sucess");
					historyRepo.save(th);
					msg.put("status", "Sucess");
					msg.put("From Amount", "" + source);
					msg.put("To Account", "" + destination);
					msg.put("Amount", "" + amount);
					return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);

				} else {
					msg.put("status", "Transaction Failed");
					msg.put("message", "Insufficient Balance");
					TransactionHistory th = new TransactionHistory(source, destination, amount, new Date(), "Failed");
					historyRepo.save(th);
					return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);

				}
			} else {
				msg.put("status", "Transaction Failed");
				msg.put("message", "Amount should be greater than 1");
				TransactionHistory th = new TransactionHistory(source, destination, amount, new Date(), "Failed");
				historyRepo.save(th);
				return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
			}
		} else {
			msg.put("status", "Transaction Failed");
			msg.put("message", "Either Source Account OR Destination Account is not Found");
			TransactionHistory th = new TransactionHistory(source, destination, amount, new Date(), "Failed");
			historyRepo.save(th);
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);

		}
	}
	
	public Customer editCustomer(Customer customer) {
		if (customer.getCustomerName().isEmpty() || customer.getCustomerGender().isEmpty()
				|| customer.getCustomerAadhar().isEmpty() || customer.getCustomerPan().isEmpty()
				|| customer.getCustomerEmail().isEmpty() || customer.getCustomerContact().isEmpty()
				|| customer.getCustomerAddress().isEmpty())
			throw new EmptyInputException("Empty input fields");

		return customerRepo.save(customer);
	}
}
