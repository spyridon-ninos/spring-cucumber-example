package com.ninos.events.integration;

import com.ninos.events.EventsRepository;
import com.ninos.events.model.Event;
import com.ninos.events.model.NoSuchEventException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MapInMemoryEventsRepository implements EventsRepository {
	private Map<Long, Event> events;

	@PostConstruct
	public void setup() {
		events = new LinkedHashMap<Long, Event>();
		for (long i=0; i<10; i++) {
			Event event = new Event();
			event.setId(Long.valueOf(i));
			event.setHome("Home_" + i);
			event.setAway("Away_" + i);

			events.put(i, event);
		}
	}

	public List<Event> getAll() {
		return new ArrayList<Event>(events.values());
	}

	public Event getById(Long id) throws NoSuchEventException {
		if (events.containsKey(id)) {
			return events.get(id);
		}

		throw new NoSuchEventException("Event with id " + id + " not found");
	}
}
