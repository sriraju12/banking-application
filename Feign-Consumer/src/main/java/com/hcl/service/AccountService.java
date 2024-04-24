package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.customexception.EmptyDataFoundInDatabaseException;
import com.hcl.customexception.EmptyInputException;
import com.hcl.model.Account;
import com.hcl.repository.AccountRepo;

@Service
public class AccountService {

	@Autowired
	AccountRepo accountRepo;

	public List<Account> getAccounts() {
		List<Account> allAccounts = accountRepo.findAll();
		if (allAccounts.isEmpty())
			throw new EmptyDataFoundInDatabaseException("No Data Found in Database");

		return allAccounts;
	}

	public void deleteAccount(Long accountId) {
		accountRepo.deleteById(accountId);
	}

}
