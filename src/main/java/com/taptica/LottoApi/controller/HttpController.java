package com.taptica.LottoApi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taptica.LottoApi.exceptions.InvalidLotterryNumberProvidedException;
import com.taptica.LottoApi.exceptions.TooManyGuessingTriesException;
import com.taptica.LottoApi.service.LottoService;

@RestController
@RequestMapping("")
public class HttpController {

	private final String SUCCESSFUL_SERV = "Guessing number {} for user {} was successfuly registered.";
	private final String TOO_MANY_GUESSES = "User {} already registered two guesses !";
	private final String GUESSING_NUMBER_IS_OUT_OF_RANG = "User {} tried to provide a guess out of valid rage !";
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpController.class);
	private final LottoService lottoServ;

	@Autowired
	public HttpController(LottoService lottoServ) {
		this.lottoServ = lottoServ;
	}

	@PostMapping("/{userId}")
	public ResponseEntity<Void> postNumber(@PathVariable("userId") String userId,
			@RequestParam("guessedNum") String guessedNum) {
		HttpStatus httpStatus;
		try {
			lottoServ.serve(userId, guessedNum);
			httpStatus = HttpStatus.OK;
			LOGGER.info(SUCCESSFUL_SERV, guessedNum, userId);
		} catch (TooManyGuessingTriesException e) {
			httpStatus = HttpStatus.TOO_MANY_REQUESTS;
			LOGGER.error(TOO_MANY_GUESSES, userId);
		} catch (InvalidLotterryNumberProvidedException e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			LOGGER.error(GUESSING_NUMBER_IS_OUT_OF_RANG, userId);
		}
		return new ResponseEntity<>(httpStatus);
	}

}
