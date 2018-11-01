package com.taptica.LottoApi.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taptica.LottoApi.controller.HttpController;

public class GuessingUsersList {

	private final static Logger LOGGER = LoggerFactory.getLogger(HttpController.class);
	private List<String> guessPool;

	public GuessingUsersList() {
		this.guessPool = new ArrayList<String>();
	}

	public synchronized void addUser(String userId) {
		guessPool.add(userId);
	}

	public void printToLog() {
		LOGGER.info("Winning Users are: " + guessPool.toString());
	}

}
