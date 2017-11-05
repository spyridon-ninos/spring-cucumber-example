package com.ninos.bets.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BetPlaceRequest {
	private Long eventId;
	private Double amountToBet;

	@JsonDeserialize(using = TeamDeserialiser.class)
	private Team winner;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Double getAmountToBet() {
		return amountToBet;
	}

	public void setAmountToBet(Double amountToBet) {
		this.amountToBet = amountToBet;
	}

	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Bet toBet() {
		Bet bet = new Bet();
		bet.setEventId(eventId);
		bet.setAmountPlaced(amountToBet);
		bet.setTeam(winner);

		return bet;
	}
}
