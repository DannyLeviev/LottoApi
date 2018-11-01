package com.taptica.LottoApi.exceptions;

public class TooManyGuessingTriesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TooManyGuessingTriesException(Long userId) {
		super("The User - " + userId + " has already submitted two guesses !!!");
	}
}
