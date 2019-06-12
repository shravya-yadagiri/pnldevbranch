package com.eagle.pnl.config;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.eagle.pnl.model.Account;
import com.eagle.pnl.model.AccountPosition;
import com.eagle.pnl.model.AccountSummaryInfo;
import com.eagle.pnl.providers.EagleAccountSummaryDataProvider;
import com.eagle.pnl.providers.EaglePositionDataProvider;
import com.eagle.pnl.repository.AccountRepository;
import com.eagle.pnl.repository.AccountRepositoryFactory;
import com.eagle.pnl.repository.InstrumentRepository;
import com.eagle.pnl.repository.InstrumentRepositoryFactory;
import com.eagle.pnl.tws.EagleAPIFactory;
import com.eagle.pnl.utils.EagleEngineFileUtils;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan("com.eagle")
public class EagleWorkFlowEngineConfiguration implements InitializingBean {
	private final static Logger LOGGER = LoggerFactory.getLogger(EagleWorkFlowEngineConfiguration.class.getName());

	@Autowired
	public DataSource dataSource;

	@Autowired
	private EagleAPIFactory eagleAPIFactory;

	@Autowired
	private EagleEngineFileUtils eagleEngineFileUtils;

	private AccountRepositoryFactory accountRepositoryFactory = new AccountRepositoryFactory();

	private InstrumentRepositoryFactory instrumentRepositoryFactory = new InstrumentRepositoryFactory();

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("EaglePnLConfiguration done...");
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); // single threaded by default
	}

	@Bean
	public InstrumentRepository instrumentRepository() throws IOException {
		PathResource pathResource = new PathResource(eagleEngineFileUtils.getInstrumentConfigFile());
		return instrumentRepositoryFactory.createInstrumentRepository(pathResource.getInputStream());
	}

	@Bean
	public AccountRepository accountRepository() throws IOException {
		PathResource pathResource = new PathResource(eagleEngineFileUtils.getAccountsConfigFile());
		AccountRepository accountRepository = accountRepositoryFactory.createAccountRepository(pathResource.getInputStream());
		if (accountRepository != null) {
			List<Account> accounts = accountRepository.getAccounts();
			for (Account account : accounts) {
				eagleAPIFactory.getEagleAPI(account.getiBAccount());
			}
		}
		return accountRepository;
	}

	@Bean
	public AccountPosition accountPosition() {
		return new AccountPosition();
	}

	@Bean
	public EaglePositionDataProvider eaglePositionDataProvider() {
		return new EaglePositionDataProvider();
	}

	@Bean
	public EagleAccountSummaryDataProvider eagleAccountSummaryDataProvider() {
		return new EagleAccountSummaryDataProvider();
	}

	@Bean
	public AccountSummaryInfo accountSummaryInfo() {
		return new AccountSummaryInfo();
	}

}
