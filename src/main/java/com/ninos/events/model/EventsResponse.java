package com.ninos.events.model;

import com.ninos.bets.model.Response;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class EventsResponse extends Response<List<Event>> {
	private List<Event> events;

	public List<Event> getResponseBody() {
		return events;
	}

	public void setResponseBody(List<Event> events) {
		this.events = events;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		EventsResponse that = (EventsResponse) o;

		return events != null ? events.equals(that.events) : that.events == null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
