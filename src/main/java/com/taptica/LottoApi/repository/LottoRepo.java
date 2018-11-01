package com.taptica.LottoApi.repository;

public interface LottoRepo {

	boolean addUserGuess(String userId, Integer guessedNum);

	void resetPools();

	void printWinningUsers(Integer lottoRunNum);

}