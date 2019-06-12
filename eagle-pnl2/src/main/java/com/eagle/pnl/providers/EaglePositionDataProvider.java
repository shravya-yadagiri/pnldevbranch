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
import com.eagle.pnl.model.AccountPnLInformation;
import com.eagle.pnl.model.AccountPosition;
import com.eagle.pnl.model.Instrument;
import com.eagle.pnl.model.InstrumentPnL;
import com.eagle.pnl.utils.EagleEngineFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ib.controller.ApiController.IAccountHandler;
import com.ib.controller.Position;

public class EaglePositionDataProvider implements IAccountHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EaglePositionDataProvider.class);

	private Instrument instrument;

	private AccountPosition accountPosition;

	private InstrumentPnL instrumentPnL;

	private static HashMap<String, AccountPosition> instrumentPositionMap = new HashMap<>();

	private static HashMap<String, InstrumentPnL> instrumentPnLMap = new HashMap<>();

	@Autowired
	private EagleEngineFileUtils eagleEngineFIleUtils;

	@Autowired
	private EagleWorkFlowEngineProperties engineProperties;

	public EaglePositionDataProvider(Instrument instrument) {
		this.instrument = instrument;
		accountPosition = new AccountPosition();
		instrumentPositionMap.put(this.instrument.getSymbol(), accountPosition);
	}

	public EaglePositionDataProvider() {
	}

	public AccountPosition getAccountPosition() {
		return accountPosition;
	}

	@Override
	public void accountDownloadEnd(String accountName) {
	}

	@Override
	public void accountTime(String timeStamp) {
	}

	@Override
	public void accountValue(String key, String value, String currency, String accountName) {
	}

	@Override
	public void updatePortfolio(Position position) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		System.out.println("capturing the pnl n other position information...");
		AccountPosition accountPosition = new AccountPosition();
		InstrumentPnL instrumentPnL = new InstrumentPnL();
		instrumentPnL.setAccountName(position.account());
		instrumentPnL.setSymbol(position.contract().symbol());
		instrumentPnL.setRealPnl(position.realPnl());
		instrumentPnL.setUnrealPnl(position.unrealPnl());
		instrumentPnL.setTotalPnl(position.realPnl() + position.unrealPnl());
		instrumentPnL.setDate(format.format(new Date()));
		accountPosition.setAccountName(position.account());
		accountPosition.setSymbol(position.contract().symbol());
		accountPosition.setRealPnl(position.realPnl());
		accountPosition.setUnrealPnl(position.unrealPnl());
		accountPosition.setTotalPnl(position.realPnl() + position.unrealPnl());
		accountPosition.setAvgCost(position.averageCost());
		accountPosition.setPosition(position.position());
		accountPosition.setMarketPrice(position.marketPrice());
		accountPosition.setMarketValue(position.marketValue());
		accountPosition.setDate(format.format(new Date()));
		accountPosition.setExpiry(position.contract().expiry());
		accountPosition.setCurrency(position.contract().currency());
		accountPosition.setContractDescription(position.contract().description());
		instrumentPositionMap.put(position.contract().symbol(), accountPosition);
		instrumentPnLMap.put(position.contract().symbol(), instrumentPnL);
	}

	public static AccountPosition getAcccountPositionData(String instrumentSymbol) {
		return instrumentPositionMap.get(instrumentSymbol);
	}

	@SuppressWarnings("unchecked")
	public void writeData(String accountName) {
		ICsvMapWriter mapWriter = null;
		Set<String> instruments = instrumentPositionMap.keySet();
		for (String instrument : instruments) {
			AccountPosition accountPosition = instrumentPositionMap.get(instrument);
			accountPosition.setIbAccount(accountName);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> beanProperties = objectMapper.convertValue(accountPosition, Map.class);
			String[] header = new String[beanProperties.size()];
			int headerIndex = 0;
			for (Map.Entry<String, Object> entry : beanProperties.entrySet()) {
				header[headerIndex] = new String(entry.getKey());
				headerIndex++;
			}
			String positionDirectory = eagleEngineFIleUtils.getOrderAckPath();
			String positionInformatioFileType = engineProperties.getRawDataFileType();
			String positionInformationPath = positionDirectory + "positions." + positionInformatioFileType;
			Path accountPositionPath = Paths.get(positionInformationPath);
			LOGGER.info("Writing the account position information in to the " + accountPositionPath + " file ..");
			try {
				if (Files.exists(accountPositionPath)) {
					mapWriter = new CsvMapWriter(new FileWriter(positionInformationPath, true), CsvPreference.STANDARD_PREFERENCE);
				} else {
					mapWriter = new CsvMapWriter(new FileWriter(positionInformationPath), CsvPreference.STANDARD_PREFERENCE);
					mapWriter.writeHeader(header);
				}
				mapWriter.write(beanProperties, header, getProcessor());
			} catch (IOException e) {
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, positionInformationPath, e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, positionInformationPath, e.getMessage());
			} finally {
				if (mapWriter != null) {
					try {
						mapWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public CellProcessor[] getProcessor() {
		CellProcessor[] processor = new CellProcessor[] { new Optional(),new Optional(), new Optional(), new Optional(), new Optional(), new Optional(),
				new Optional(), new Optional(), new Optional(), new Optional(), new Optional(), new Optional(), new Optional(), new Optional() };
		return processor;
	}

	@SuppressWarnings("unchecked")
	public void savePnlData(String accountName) {
		double totalRealPnl = 0;
		double totalUnrealPnl = 0;
		ICsvMapWriter mapWriter = null;
		Set<String> instruments = instrumentPositionMap.keySet();
		AccountPnLInformation accountPnLInformation = new AccountPnLInformation();
		accountPnLInformation.setIbaccount(accountName);

		// calculating the total realpnl,unrealpnl of an account
		for (String instrument : instruments) {
			AccountPosition accountPosition = instrumentPositionMap.get(instrument);
			totalRealPnl = totalRealPnl + accountPosition.getRealPnl();
			totalUnrealPnl = totalUnrealPnl + accountPosition.getUnrealPnl();
			accountPnLInformation.setAccount(accountPosition.getAccountName());
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		accountPnLInformation.setDate(formatter.format(new Date()));
		accountPnLInformation.setRealPnL(totalRealPnl);
		accountPnLInformation.setUnrealPnL(totalUnrealPnl);
		accountPnLInformation.setTotalPnL(totalUnrealPnl + totalRealPnl);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> beanProperties = objectMapper.convertValue(accountPnLInformation, Map.class);
		String[] header = new String[beanProperties.size()];
		int headerIndex = 0;
		for (Map.Entry<String, Object> entry : beanProperties.entrySet()) {
			header[headerIndex] = new String(entry.getKey());
			headerIndex++;
		}
		String positionDirectory = eagleEngineFIleUtils.getOrderAckPath();
		String pnlinformationFileType = engineProperties.getRawDataFileType();
		String pnlInformationPath = positionDirectory + "dailypnl." + pnlinformationFileType;
		Path pnlinformationStorePath = Paths.get(pnlInformationPath);
		LOGGER.info("Saving PnL information in to the  csv file ::" + pnlinformationStorePath);
		try {
			if (Files.exists(pnlinformationStorePath)) {
				mapWriter = new CsvMapWriter(new FileWriter(pnlInformationPath, true), CsvPreference.STANDARD_PREFERENCE);
			} else {
				mapWriter = new CsvMapWriter(new FileWriter(pnlInformationPath), CsvPreference.STANDARD_PREFERENCE);
				mapWriter.writeHeader(header);
			}
			mapWriter.write(beanProperties, header, getPnlProcessor());
		} catch (IOException e) {
			throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, pnlInformationPath, e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, pnlInformationPath, e.getMessage());
		} finally {
			if (mapWriter != null) {
				try {
					mapWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public CellProcessor[] getPnlProcessor() {
		CellProcessor[] processor = new CellProcessor[] { new Optional(),new Optional(), new Optional(), new Optional(), new Optional(), new Optional() };
		return processor;
	}

	@SuppressWarnings("unchecked")
	public void saveInstrumentPnL(String accountName) {
		ICsvMapWriter mapWriter = null;
		Set<String> instruments = instrumentPnLMap.keySet();
		for (String instrument : instruments) {
			InstrumentPnL instrumentPnL = instrumentPnLMap.get(instrument);
			instrumentPnL.setIbAccount(accountName);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> beanProperties = objectMapper.convertValue(instrumentPnL, Map.class);
			String[] header = new String[beanProperties.size()];
			int headerIndex = 0;
			for (Map.Entry<String, Object> entry : beanProperties.entrySet()) {
				header[headerIndex] = new String(entry.getKey());
				headerIndex++;
			}
			String PnlDirectory = eagleEngineFIleUtils.getOrderAckPath();
			String PnLInformatioFileType = engineProperties.getRawDataFileType();
			String pnlInformationPath = PnlDirectory + "instrumentPnL." + PnLInformatioFileType;
			Path instrumentPnlPath = Paths.get(pnlInformationPath);
			LOGGER.info("Writing the instrument level PnL information in to the " + instrumentPnlPath + " file ..");
			try {
				if (Files.exists(instrumentPnlPath)) {
					mapWriter = new CsvMapWriter(new FileWriter(pnlInformationPath, true), CsvPreference.STANDARD_PREFERENCE);
				} else {
					mapWriter = new CsvMapWriter(new FileWriter(pnlInformationPath), CsvPreference.STANDARD_PREFERENCE);
					mapWriter.writeHeader(header);
				}
				mapWriter.write(beanProperties, header, getInstrumentPnlProcessor());
			} catch (IOException e) {
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, pnlInformationPath, e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EagleException(EagleError.FAILD_TO_WRITE_DATA_INFILE, pnlInformationPath, e.getMessage());
			} finally {
				if (mapWriter != null) {
					try {
						mapWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public CellProcessor[] getInstrumentPnlProcessor() {
		CellProcessor[] processor = new CellProcessor[] { new Optional(), new Optional(), new Optional(), new Optional(), new Optional(),
				new Optional(), new Optional() };
		return processor;
	}
}
