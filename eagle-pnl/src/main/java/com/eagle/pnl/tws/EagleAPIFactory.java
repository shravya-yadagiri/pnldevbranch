package com.eagle.pnl.tws;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eagle.pnl.model.IBAccount;
import com.eagle.pnl.providers.EagleTWSConnectionProvider;

@Component
public class EagleAPIFactory {

	@Autowired
	private EagleTWSConnectionProvider connectionProvider;

	private static ConcurrentHashMap<String, EagleAPI> eagleApiMap = new ConcurrentHashMap<>();

	@Autowired
	private EagleAPILogger eagleAPILogger;

	public EagleAPI getEagleAPI(IBAccount ibAccount) {
		EagleAPI eagleApi = null;
		if (eagleApiMap.get(ibAccount.getAccountName()) != null) {
			eagleApi = eagleApiMap.get(ibAccount.getAccountName());
		} else {
			eagleApi = new EagleAPI(new EagleTWSConnectionProvider(), eagleAPILogger, eagleAPILogger, ibAccount.getHost(), ibAccount.getPort(),
					ibAccount.getClientId());
			eagleApiMap.put(ibAccount.getAccountName(), eagleApi);
		}
		return eagleApi;
	}
}
