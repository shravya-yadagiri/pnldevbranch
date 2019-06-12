package com.eagle.pnl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eagle.pnl.model.Account;
import com.eagle.pnl.providers.EagleAccountSummaryDataProvider;
import com.eagle.pnl.providers.EaglePositionDataProvider;
import com.eagle.pnl.repository.AccountRepository;
import com.eagle.pnl.tws.EagleAPI;
import com.eagle.pnl.tws.EagleAPIFactory;
import com.eagle.pnl.tws.EagleTWSClient;
import com.ib.controller.AccountSummaryTag;

@Service
public class AccountInfoScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountInfoScheduler.class);

	@Autowired
	private EagleTWSClient eagleTWSClient;

	@Autowired
	private EaglePositionDataProvider eaglePositionDataProvider;
	
	@Autowired
	private EagleAccountSummaryDataProvider eagleAccountSummaryDataProvider;

	private EagleAPI eagleAPI;

	@Autowired
	private EagleAPIFactory eagleAPIFactory;

	@Autowired
	private AccountRepository accountRepository;

	@Scheduled(cron = "0 30 15 * * ?")
	//@Scheduled(cron = "0 0/10 * * * ?")
	public void runAccountInfoScheduler() {
		try {
			LOGGER.debug("Account information scheduler process started........");
			List<Account> accounts = accountRepository.getAccounts();
			for (Account account : accounts) {
				eagleTWSClient.setEagleAPI(eagleAPIFactory.getEagleAPI(account.getiBAccount()));
				eagleAPI = eagleTWSClient.getEagleAPI();
				List<String> accountList = eagleTWSClient.getAccounts();
				for (String account1 : accountList) {
					eagleAPI.reqAccountUpdates(true, account1, eaglePositionDataProvider);
					LOGGER.debug("reqAccountUpdates has been invoked .......");
					Thread.sleep(5000);
					eaglePositionDataProvider.writeData(account.getName());
					eaglePositionDataProvider.savePnlData(account.getName());
					eaglePositionDataProvider.saveInstrumentPnL(account.getName());
					eagleAPI.reqAccountUpdates(false,account1, eaglePositionDataProvider);
				}
			}	
			LOGGER.debug("Account information scheduler process completed........");
		} catch (Exception e) {
			LOGGER.error("Account information scheduler process Failed..", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 45 15 * * ?")
	//@Scheduled(cron = "0 0/13 * * * ?")
	public void runAccountBalanceScheduler() {
		try {
			LOGGER.debug("Account balance scheduler process started........");
			AccountSummaryTag[] tags = { AccountSummaryTag.NetLiquidation, AccountSummaryTag.EquityWithLoanValue, AccountSummaryTag.TotalCashValue,
					AccountSummaryTag.SMA, AccountSummaryTag.RegTEquity };
			List<Account> accounts = accountRepository.getAccounts();
			for (Account account : accounts) {
				eagleTWSClient.setEagleAPI(eagleAPIFactory.getEagleAPI(account.getiBAccount()));
				eagleAPI = eagleTWSClient.getEagleAPI();
				eagleAPI.reqAccountSummary("All", tags, eagleAccountSummaryDataProvider);
				Thread.sleep(10000);
				eagleAccountSummaryDataProvider.storeBalance(account.getName());
			}
			LOGGER.debug("Account balance scheduler process completed........");
		} catch (Exception e) {
			LOGGER.error("Account balance scheduler process Failed..", e.getMessage(), e);
			e.printStackTrace();
		}

	}

}
