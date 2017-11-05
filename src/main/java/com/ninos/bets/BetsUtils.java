package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.BetsResponse;
import com.ninos.bets.model.Error;
import com.ninos.bets.model.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public final class BetsUtils {

	private BetsUtils() {
		// nothing - forbid instantiation
	}

	public static List<Error> buildErrors(ErrorType type, String message) {
		Error error = new Error();
		error.setErrorType(type);
		error.setMessage(message);

		List<Error> errorList = new ArrayList<>();
		errorList.add(error);

		return errorList;
	}

	public static BetsResponse buildNoErrorResponse(Supplier<List<Bet>> betSupplier) {
		return buildGenericBetsResponse(null, betSupplier);
	}

	public static BetsResponse buildErrorResponse(Supplier<List<Error>> errorSupplier) {
		return buildGenericBetsResponse(errorSupplier, null);
	}

	public static BetsResponse buildGenericBetsResponse(
			Supplier<List<Error>> errorSupplier,
			Supplier<List<Bet>> betSupplier
	) {

		List<Error> errorList = Optional.ofNullable(errorSupplier)
		                                .map(Supplier::get)
		                                .orElse(Collections.emptyList());

		List<Bet> betList = Optional.ofNullable(betSupplier)
		                            .map(Supplier::get)
		                            .orElse(Collections.emptyList());

		BetsResponse betsResponse = new BetsResponse();
		betsResponse.setErrors(errorList);
		betsResponse.setResponseBody(betList);

		return betsResponse;
	}
}
