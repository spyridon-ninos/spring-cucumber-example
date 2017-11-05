package com.ninos.bets.model;

public class NoSuchBetException extends Exception {

	public NoSuchBetException() {
		// nothing
	}

	public NoSuchBetException(String message) {
		super(message);
	}
}
