package com.eagle.pnl.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eagle.pnl.model.Account;
import com.eagle.pnl.model.AccountInstrument;

public class AccountRepository {

	private Map<String, Account> accountRepository = new HashMap<>();

	public AccountRepository(List<Account> accounts) {
		for (Account account : accounts) {
			accountRepository.put(account.getName(), account);
		}
	}

	public List<Account> getAccounts() {
		List<Account> accountList = new ArrayList<>(accountRepository.size());
		for (Map.Entry<String, Account> entry : accountRepository.entrySet()) {
			accountList.add(entry.getValue());
		}
		return accountList;
	}

	public Account getAccount(String name) {
		return accountRepository.get(name);
	}

	public Account getPrimaryAccount() {
		List<Account> accountList = new ArrayList<>(accountRepository.size());
		Account account = null;
		for (Map.Entry<String, Account> entry : accountRepository.entrySet()) {
			account = entry.getValue();
			if (account.isPrimary()) {
				return account;
			}
		}
		return null;
	}

	public AccountInstrument getInstrument(Account account, String instrumentName) {
		List<AccountInstrument> instruments = account.getInstruments();
		for (AccountInstrument accountInstrument : instruments) {
			if (instrumentName.equals(accountInstrument.getName())) {
				return accountInstrument;
			}
		}
		return null;
	}
}
