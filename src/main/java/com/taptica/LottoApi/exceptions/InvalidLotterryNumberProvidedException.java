package com.taptica.LottoApi.exceptions;

public class InvalidLotterryNumberProvidedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidLotterryNumberProvidedException(String userId) {
		super("User " + userId + " provided a wrong guessing number. Try again in a range 1-100 !!!");
	}
}
