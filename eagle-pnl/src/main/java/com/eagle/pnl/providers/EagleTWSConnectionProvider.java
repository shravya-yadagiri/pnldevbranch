
package com.eagle.pnl.providers;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eagle.pnl.exception.EagleError;
import com.eagle.pnl.exception.EagleException;
import com.ib.controller.ApiController.IConnectionHandler;

@Component
public class EagleTWSConnectionProvider implements IConnectionHandler {
	private Logger LOGGER = LoggerFactory.getLogger(EagleTWSConnectionProvider.class);
	
	private static boolean connected;
	
	private static int errorCode;
	
	private ArrayList<String> mAccountList = null;
	
	public EagleTWSConnectionProvider(){
		LOGGER.info("in EagleTWSConnectionProvider");
	}
	
	public boolean isConnected() {
		return connected;
	}

	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#accountList(java.util.ArrayList)
	 */
	@Override
	public void accountList(ArrayList<String> accountList) {
		LOGGER.info("EagleTWSConnectionProvider -> TWS accountList ["+accountList+"]");
		mAccountList = accountList;
	}

	public ArrayList<String> getAccountList() {
		return mAccountList;
	}
	
	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#connected()
	 */
	@Override
	public void connected() {
		LOGGER.info("EagleTWSConnectionProvider -> TWS Connected");
		connected = true;
	}

	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#disconnected()
	 */
	@Override
	public void disconnected() {
		LOGGER.info("EagleTWSConnectionProvider -> TWS disConnected");
		connected = false;
	}

	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#error(java.lang.Exception)
	 */
	@Override
	public void error(Exception exception) {
		exception.printStackTrace();
		LOGGER.error("EagleTWSConnectionProvider -> TWS Error, ",exception.getMessage(), exception);
		throw new EagleException(EagleError.FAILED_TO_CONNECT_TWS, exception.getMessage(), exception);
	}

	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#message(int, int, java.lang.String)
	 */
	@Override
	public void message(int id, int messageCode, String message) {
		LOGGER.info("EagleTWSConnectionProvider -> TWS Message -["+id + " - "  + messageCode + " - " +message+"]");
		errorCode=messageCode;
	}

	/* (non-Javadoc)
	 * @see com.ib.controller.ApiController.IConnectionHandler#show(java.lang.String)
	 */
	@Override
	public void show(String arg0) {
		LOGGER.info("EagleTWSConnectionProvider -> TWS Show -["+arg0+"]");
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int code) {
		errorCode = code;
	}
}
