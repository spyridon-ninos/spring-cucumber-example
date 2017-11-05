package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.BetCancelRequest;
import com.ninos.bets.model.BetPlaceRequest;
import com.ninos.bets.model.BetsResponse;
import com.ninos.bets.model.ErrorType;
import com.ninos.bets.model.NoSuchBetException;
import com.ninos.events.model.NoSuchEventException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.ninos.bets.BetsUtils.buildErrorResponse;
import static com.ninos.bets.BetsUtils.buildErrors;
import static com.ninos.bets.BetsUtils.buildNoErrorResponse;

@RestController
@RequestMapping(
		value = "/api/${app.api.version}/bets",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api
public final class BetsEndpoint {

	private static Logger logger = LoggerFactory.getLogger(BetsEndpoint.class);

	private final BetsService betsService;

	@Inject
	public BetsEndpoint(
		BetsService betsService
	) {
		this.betsService = betsService;
	}

	@GetMapping
	@ApiOperation(value = "get the history of all bets", response = BetsResponse.class)
	public BetsResponse getAll() {
		return buildNoErrorResponse(betsService::getAll);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "get information of a single bet", response = BetsResponse.class)
	public BetsResponse getById(@PathVariable("id") Long id) throws NoSuchBetException {
		List<Bet> betList = new ArrayList<>();
		betList.add(betsService.getById(id));
		return buildNoErrorResponse(() -> betList);
	}

	@PostMapping
	@ApiOperation(value = "place a bet", response = BetsResponse.class)
	public BetsResponse save(@RequestBody BetPlaceRequest betPlaceRequest) throws NoSuchEventException {
		Bet savedBet = betsService.save(betPlaceRequest.toBet());
		return buildNoErrorResponse(() -> Arrays.asList(savedBet));
	}

	@DeleteMapping
	@ApiOperation(value = "cancel a bet", response = BetsResponse.class)
	public BetsResponse cancel(@RequestBody BetCancelRequest betCancelRequest) throws NoSuchBetException {
		Bet cancelledBet = betsService.cancel(betCancelRequest.toBet());
		return buildNoErrorResponse(() -> Arrays.asList(cancelledBet));
	}

	@GetMapping(value = "/placed")
	@ApiOperation(value = "get all placed and not cancelled, not settled bets", response = BetsResponse.class)
	public BetsResponse getActive() {
		return buildNoErrorResponse(betsService::getActive);
	}

	@GetMapping(value = "/cancelled")
	@ApiOperation(value = "get all cancelled bets", response = BetsResponse.class)
	public BetsResponse getCancelled() {
		return buildNoErrorResponse(betsService::getCancelled);
	}

	@GetMapping(value = "/settled")
	@ApiOperation(value = "get all settled events", response = BetsResponse.class)
	public BetsResponse getSettled() {
		return buildNoErrorResponse(betsService::getSettled);
	}

	// maybe NOT_FOUND allows for enumeration? But in a production system authorisation should have been
	// applied so anyone who's authorised to see bets, he should be allowed to enumerate bets
	@ExceptionHandler(value = {NoSuchBetException.class, NoSuchEventException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public BetsResponse handleNoSuchExceptions(Exception e) {
		logger.error("The requested resource was not found: {}", e.getMessage(), e);
		return buildErrorResponse(() -> buildErrors(ErrorType.NO_SUCH_RESOURCE, e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BetsResponse handleGeneralException(Exception e) {
		String exceptionId = UUID.randomUUID().toString();
		logger.error("{} Received an exception: {}", exceptionId, e.getMessage(), e);
		return buildErrorResponse(() -> buildErrors(ErrorType.UNKNOWN_ERROR, exceptionId));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BetsResponse handleMessageNotReadableException(HttpMessageNotReadableException e) {
		String message = e.getCause().getCause().getMessage();
		logger.error("The message could not be read: {}", message, e);
		return buildErrorResponse(() -> buildErrors(ErrorType.BAD_REQUEST, message));
	}
}
