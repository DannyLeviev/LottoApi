package com.taptica.LottoApi.repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taptica.LottoApi.controller.HttpController;
import com.taptica.LottoApi.model.GuessingUsersList;

@Component
public class LottoRepoImpl implements LottoRepo {

	private final String NO_WINNERS_MSG = "The lucky number is {}, but there is no winners this time !!!";
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpController.class);
	private final ReentrantLock lock = new ReentrantLock();
	private final Map<Integer, GuessingUsersList> numPool = new ConcurrentHashMap<>();
	private final Set<String> usersPool = new HashSet<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taptica.LottoApi.repository.LottoRepo#addUserGuess(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public boolean addUserGuess(String userId, Integer guessedNum) {
		boolean result = false;
		lock.lock();
		try {
			if (!usersPool.contains(userId + "_1st")) {
				usersPool.add(userId + "_1st");
				numPool.put(guessedNum, new GuessingUsersList());
				numPool.get(guessedNum).addUser(userId);
				result = true;
			} else if (!usersPool.contains(userId + "_2nd")) {
				usersPool.add(userId + "_2nd");
				numPool.get(guessedNum).addUser(userId);
				result = true;
			}
		} finally {
			lock.unlock();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taptica.LottoApi.repository.LottoRepo#resetPools()
	 */
	@Override
	public void resetPools() {
		lock.lock();
		try {
			numPool.clear();
			usersPool.clear();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taptica.LottoApi.repository.LottoRepo#printWinningUsers(java.lang.
	 * Integer)
	 */
	@Override
	public void printWinningUsers(Integer lottoRunNum) {
		if (numPool.containsKey(lottoRunNum)) {
			numPool.get(lottoRunNum).printToLog();
		}
		else {
			LOGGER.info(NO_WINNERS_MSG, lottoRunNum);
		}
	}

}
