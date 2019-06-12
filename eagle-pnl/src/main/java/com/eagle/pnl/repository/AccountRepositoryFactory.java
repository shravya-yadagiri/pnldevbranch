package com.eagle.pnl.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.eagle.pnl.model.Account;
import com.eagle.pnl.model.AccountInstrument;
import com.eagle.pnl.model.IBAccount;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountRepositoryFactory {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private InstrumentRepository instrumentRepository;

	public AccountRepository createAccountRepository(InputStream accountInputStream) {
		List<Map<String, Object>> dataMap;
		try {
			dataMap = objectMapper.readValue(accountInputStream, new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<Account> accountLists = new ArrayList<>();
		Account account = null;
		for (Map<String, Object> row : dataMap) {
			account = new Account();
			account.setName((String) row.get("name"));
			account.setPrimary((boolean) row.get("primary"));

			Map<String, Object> ibA = (Map<String, Object>) row.get("iBAccount");
			IBAccount ibAccount = new IBAccount();
			ibAccount.setClientId((Integer) ibA.get("clientId"));
			ibAccount.setHost((String) ibA.get("host"));
			ibAccount.setPort((int) ibA.get("port"));
			ibAccount.setAccountName(account.getName());
			account.setiBAccount(ibAccount);

			List<Map<String, Object>> accountInstruments = ((List<Map<String, Object>>) row.get("instruments"));
			AccountInstrument accountInstrument = null;
			List<AccountInstrument> accountInstrumentLists = new ArrayList<>();
			for (Map<String, Object> aInstrument : accountInstruments) {
				accountInstrument = new AccountInstrument();
				accountInstrument.setName((String) aInstrument.get("name"));
				accountInstrument.setLeverageFactor((Integer) aInstrument.get("leverage_factor"));
				accountInstrumentLists.add(accountInstrument);
				accountInstrument = null;
			}
			account.setInstruments(accountInstrumentLists);
			accountLists.add(account);
			account = null;
		}
		return new AccountRepository(accountLists);
	}
}
