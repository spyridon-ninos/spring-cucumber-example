package com.ninos.bets.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bet {
	private Long id;
	private Long eventId;
	private Team team;
	private BetStatus status;
	private Double amountPlaced;
	private Double amountRewarded;
	private Boolean punterWon;
	private LocalDateTime whenPlaced;
	private LocalDateTime whenStatusChanged;

	public Bet() {
		whenPlaced = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public BetStatus getStatus() {
		return status;
	}

	public void setStatus(BetStatus status) {
		this.status = status;
	}

	public Double getAmountPlaced() {
		return amountPlaced;
	}

	public void setAmountPlaced(Double amountPlaced) {
		this.amountPlaced = amountPlaced;
	}

	public Double getAmountRewarded() {
		return amountRewarded;
	}

	public void setAmountRewarded(Double amountRewarded) {
		this.amountRewarded = amountRewarded;
	}

	public Boolean getPunterWon() {
		return punterWon;
	}

	public void setPunterWon(Boolean punterWon) {
		this.punterWon = punterWon;
	}

	public String getWhenPlaced() {
		return whenPlaced.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public String getWhenStatusChanged() {
		if (whenStatusChanged == null) {
			return getWhenPlaced();
		} else {
			return whenStatusChanged.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
	}

	public void setWhenStatusChanged(LocalDateTime whenStatusChanged) {
		this.whenStatusChanged = whenStatusChanged;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
