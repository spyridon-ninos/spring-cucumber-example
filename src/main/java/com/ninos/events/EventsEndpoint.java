package com.ninos.events;

import com.ninos.bets.model.ErrorType;
import com.ninos.events.model.Event;
import com.ninos.events.model.EventsResponse;
import com.ninos.events.model.NoSuchEventException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/eventsService", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EventsEndpoint {

	private EventsService eventsService;

	@Inject
	public EventsEndpoint(
		EventsService eventsService
	) {
		this.eventsService = eventsService;
	}

	@GetMapping
	public EventsResponse getEvents() {
		EventsResponse response = new EventsResponse();
		response.setResponseBody(eventsService.getAll());
		return response;
	}

	@GetMapping(value = "/{id}")
	public EventsResponse getEvent(@PathVariable("id") Long id) throws NoSuchEventException {
		EventsResponse response = new EventsResponse();
		List<Event> eventList = new ArrayList<Event>();
		eventList.add(eventsService.getById(id));
		response.setResponseBody(eventList);

		return response;
	}

	@ExceptionHandler(NoSuchEventException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public EventsResponse handleNoSuchEvent(NoSuchEventException e) {
		return EventsUtils.createErrorResponse(ErrorType.NO_SUCH_RESOURCE, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public EventsResponse handleException(Exception e) {
		return EventsUtils.createErrorResponse(ErrorType.UNKNOWN_ERROR, e.getMessage());
	}

}
