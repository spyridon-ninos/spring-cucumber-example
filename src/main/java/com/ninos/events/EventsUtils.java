package com.ninos.events;

import com.ninos.bets.model.Error;
import com.ninos.bets.model.ErrorType;
import com.ninos.events.model.EventsResponse;

import java.util.ArrayList;
import java.util.List;

public final class EventsUtils {

	private EventsUtils() {
		// nothing, forbid instantiation
	}

	public static EventsResponse createErrorResponse(ErrorType type, String message) {
		Error error = new Error();
		error.setErrorType(type);
		error.setMessage(message);

		List<Error> errors = new ArrayList<Error>();
		errors.add(error);

		EventsResponse response = new EventsResponse();
		response.setErrors(errors);

		return response;
	}
}
