package com.ninos.events.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Event {
	private Long id;
	private String home;
	private String away;

	public Event() {
		// nothing
	}

	public Event(Long id, String home, String away) {
		this.id = id;
		this.home = home;
		this.away = away;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Event event = (Event) o;

		if (id != null ? !id.equals(event.id) : event.id != null) {
			return false;
		}
		if (home != null ? !home.equals(event.home) : event.home != null) {
			return false;
		}
		return away != null ? away.equals(event.away) : event.away == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (home != null ? home.hashCode() : 0);
		result = 31 * result + (away != null ? away.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
