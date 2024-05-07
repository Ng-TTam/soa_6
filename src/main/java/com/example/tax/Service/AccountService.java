package com.example.tax.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tax.Entity.Account;
import com.example.tax.Repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public boolean checkAccount (String user, String pass) {
		return accountRepository.checkExistsAccount(user, pass);
	}
	
	public Account getAccountLogin (String user, String pass) {
		return accountRepository.getAccountByUsernameAndPassword(user, pass);
	}
}
