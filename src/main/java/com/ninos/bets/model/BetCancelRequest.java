package com.ninos.bets.model;

public class BetCancelRequest {
	private Long betId;

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public Bet toBet() {
		Bet bet = new Bet();
		bet.setId(betId);

		return bet;
	}
}
