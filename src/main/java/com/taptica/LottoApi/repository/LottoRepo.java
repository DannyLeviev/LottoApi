package com.taptica.LottoApi.repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.taptica.LottoApi.model.GuessingUsersList;

@Component
public class LottoRepo {

	private final ReentrantLock lock = new ReentrantLock();
	private final Map<Integer, GuessingUsersList> numPool = new ConcurrentHashMap<>();
	private final Set<String> usersPool = new HashSet<>();

	public boolean addUserGuess(String userId, Integer guessedNum) {
		boolean result = false;
		lock.lock();
		try {
			if (!usersPool.contains(userId + "_1st")) {
				usersPool.add(userId + "_1st");
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

	public void resetPools() {
		lock.lock();
		try {
			numPool.clear();
			usersPool.clear();
		} finally {
			lock.unlock();
		}
	}

	public void printWinningUsers(Integer lottoRunNum) {
		if (numPool.containsKey(lottoRunNum)) {
			numPool.get(lottoRunNum).printToLog();
		}
	}

}
