package com.eagle.pnl.providers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.eagle.pnl.config.EagleWorkFlowEngineProperties;
import com.eagle.pnl.exception.EagleError;
import com.eagle.pnl.exception.EagleException;
import com.eagle.pnl.model.AccountSummaryInfo;
import com.eagle.pnl.utils.EagleEngineFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ib.controller.AccountSummaryTag;
import com.ib.controller.ApiController.IAccountSummaryHandler;

public class EagleAccountSummaryDataProvider implements IAccountSummaryHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EagleAccountSummaryDataProvider.class);

	private static HashMap<String, AccountSummaryInfo> accountsummarymap = new HashMap<>();

	private static HashMap<AccountSummaryTag, String> balanceValues = new HashMap<>();

	@Autowired
	private EagleEngineFileUtils eagleEngineFIleUtils;

	@Autowired
	private EagleWorkFlowEngineProperties engineProperties;

	public EagleAccountSummaryDataProvider(EagleWorkFlowEngineProperties engineProperties) {
		this.engineProperties = engineProperties;
	}

	public EagleAccountSummaryDataProvider() {
	}

	@Override
	public void accountSummary(String account, AccountSummaryTag tag, String value, String currency) {
		accountsummarymap.clear();
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
		AccountSummaryInfo accountSummaryInfo = new AccountSummaryInfo();
		accountSummaryInfo.setAccount(account);
		// used map for storing AccountSummaryTag and value below because every
		// tag comes in seperate callback
		balanceValues.put(tag, value);
		accountSummaryInfo.setCurrency(currency);
		accountSummaryInfo.setDate(formater.format(new Date()));
		accountsummarymap.put(account, accountSummaryInfo);
	}

	@Override
	public void accountSummaryEnd() {

	}

	@SuppressWarnings("unchecked")
	public void storeBalance(String ibAccount) {
		ICsvMapWriter mapWriter = null;
		Set<String> tags = accountsummarymap.keySet();
		for (String account : tags) {
			AccountSummaryInfo accountSummaryInfo = accountsummarymap.get(account);

			// getting all the AccountSummaryTag under one object
			accountSummaryInfo.setEquityWithLoanValue(balanceValues.get(AccountSummaryTag.EquityWithLoanValue));
			accountSummaryInfo.setNetLiquidation(balanceValues.get(AccountSummaryTag.NetLiquidation));
			accountSummaryInfo.setRegTEquity(balanceValues.get(AccountSummaryTag.RegTEquity));
			accountSummaryInfo.setTotalCashValue(balanceValues.get(AccountSummaryTag.TotalCashValue));
			accountSummaryInfo.setSMA(balanceValues.get(AccountSummaryTag.SMA));
			accountSummaryInfo.setIbAccount(ibAccount);

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> beanProperties = objectMapper.convertValue(accountSummaryInfo, Map.class);
			String[] header = new String[beanProperties.size()];
			int headerIndex = 0;
			for (Map.Entry<String, Object> entry : beanProperties.entrySet()) {
				header[headerIndex] = new String(entry.getKey());
				headerIndex++;
			}
			String positionDirectory = eagleEngineFIleUtils.getOrderAckPath();
			String accountBalanceFileType = engineProperties.getRawDataFileType();
			String balanceStorePath = positionDirectory + "accountBalance." + accountBalanceFileType;
			Path accountBalancePath = Paths.get(balanceStorePath);
			LOGGER.info("Storing Account balance in the file..." + accountBalancePath);
			try {
				if (Files.exists(accountBalancePath))
					mapWriter = new CsvMapWriter(new FileWriter(balanceStorePath, true), CsvPreference.STANDARD_PREFERENCE);
				else {
					mapWriter = new CsvMapWriter(new FileWriter(balanceStorePath), CsvPreference.STANDARD_PREFERENCE);
					mapWriter.writeHeader(header);
				}
				mapWriter.write(beanProperties, header, getProcessor());
			} catch (IOException e) {
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, balanceStorePath, e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, balanceStorePath, e.getMessage());
			} finally {
				if (mapWriter != null)
					try {
						mapWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		
	}

	public CellProcessor[] getProcessor() {
		CellProcessor[] processor = new CellProcessor[] { new Optional(),new Optional(), new Optional(), new Optional(), new Optional(), new Optional(),
				new Optional(), new Optional(), new Optional() };
		return processor;
	}

}
