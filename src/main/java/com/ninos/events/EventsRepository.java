package com.ninos.events;

import com.ninos.events.model.Event;
import com.ninos.events.model.NoSuchEventException;

import java.util.List;

// named repository because we don't expect different types to be returned
public interface EventsRepository {
	List<Event> getAll();
	Event getById(Long id) throws NoSuchEventException;
}
