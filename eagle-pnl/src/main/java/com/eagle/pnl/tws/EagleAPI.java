package com.eagle.pnl.tws;

import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eagle.pnl.exception.EagleError;
import com.eagle.pnl.exception.EagleException;
import com.eagle.pnl.providers.EagleTWSConnectionProvider;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController;

public class EagleAPI extends ApiController {

	private Logger LOGGER = LoggerFactory.getLogger(EagleAPI.class);

	private EagleTWSConnectionProvider connectionProvider;

	private EagleAPIConnection eagleAPIConnection;

	/**
	 * @param connectionProvider
	 * @param inLogger
	 * @param outLogger
	 * @param host
	 * @param port
	 * @param clientId
	 */

	public EagleAPI(EagleTWSConnectionProvider connectionProvider, ILogger inLogger, ILogger outLogger, String host, int port, int clientId) {
		super(connectionProvider, inLogger, outLogger);
		this.connectionProvider = connectionProvider;
		LOGGER.debug("ApiController Initialized. Trying to connect TWS.. [" + host + "," + port + "," + clientId + "]");
		eagleAPIConnection = new EagleAPIConnection(host, port, clientId);
		this.connect(host, port, clientId);
	}

	@Override
	public void connect(String host, int port, int clientId) {
		try {
			System.out.println("in EagleAPI conncet call from IB...");
			LOGGER.info("Trying to connect to TWS:" + Calendar.getInstance().getTime().toString() + " host=" + host + " port=" + port + "clientId="+ clientId);
			super.connect(host, port, clientId);
			if (isConnected()) {
				LOGGER.debug("TWS Connected.");
			} else {
				LOGGER.error("TWS not Connected. Please make sure TWS is up and running.");
			}
		} catch (EagleException e) {
			this.connectionProvider.disconnected();
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.connectionProvider.disconnected();
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			throw new EagleException(EagleError.FAILED_TO_CONNECT_TWS, e);
		}
	}

	@Override
	public void disconnect() {
		super.disconnect();
	}

	public boolean isConnected() {
		return this.connectionProvider.isConnected();
	}

	public void checkAndConnect() throws EagleException {
		try {
			if (!isConnected()) {
				LOGGER.debug("TWS not connected or connection lost. Trying to connect TWS.. ");
				this.connect(eagleAPIConnection.getHost(), eagleAPIConnection.getPort(), eagleAPIConnection.getClientId());
			}
		} catch (EagleException e) {
			this.connectionProvider.disconnected();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			this.connectionProvider.disconnected();
			e.printStackTrace();
			throw new EagleException(EagleError.FAILED_TO_CONNECT_TWS, e.getMessage(), e);
		}
	}

	public ArrayList<String> getAccounts() {
		try {
			checkAndConnect();
			System.out.println("getAccountList::"+this.connectionProvider.getAccountList());
			return this.connectionProvider.getAccountList();
		} catch (EagleException e) {
			this.connectionProvider.disconnected();
			throw e;
		} catch (Exception e) {
			this.connectionProvider.disconnected();
			throw new EagleException(EagleError.FAILED_TO_CONNECT_TWS, e.getMessage(), e);
		}
	}

}