package com.ninos.bets.model;

import java.util.List;

public abstract class Response<T> {
	private List<Error> errors;

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public abstract T getResponseBody();
}
