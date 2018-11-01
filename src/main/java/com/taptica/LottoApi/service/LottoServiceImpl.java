package com.taptica.LottoApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taptica.LottoApi.exceptions.InvalidLotterryNumberProvidedException;
import com.taptica.LottoApi.repository.LottoRepo;

@Service
public class LottoServiceImpl implements LottoService {

	@Autowired
	private LottoRepo lottoRepo;

	@Override
	public boolean serve(String userId, String guessedNumStr) {
		Integer guessedNum = Integer.parseInt(guessedNumStr);
		if (guessedNum < 0 || guessedNum > 100) {
			throw new InvalidLotterryNumberProvidedException(userId);
		}
		return lottoRepo.addUserGuess(userId, guessedNum);
	}

}
