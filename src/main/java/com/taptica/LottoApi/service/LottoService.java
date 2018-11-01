package com.taptica.LottoApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taptica.LottoApi.repository.LottoRepo;

@Service
public class LottoService {

	@Autowired
	private LottoRepo lottoRepo;

	public boolean serve(String userId, String guessedNumStr) {
		// validate the guessedNum: <TBD>
		Integer guessedNum = Integer.parseInt(guessedNumStr);
		return lottoRepo.addUserGuess(userId, guessedNum);
	}

}