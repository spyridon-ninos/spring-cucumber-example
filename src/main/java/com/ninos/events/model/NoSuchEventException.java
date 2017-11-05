package com.ninos.events.model;

import com.ninos.bets.model.NoSuchBetException;

public class NoSuchEventException extends Exception {

	public NoSuchEventException() {
		// nothing
	}

	public NoSuchEventException(String message) {
		super(message);
	}
}
