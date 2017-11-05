package com.ninos.bets.model;

import java.util.List;

public class BetsResponse extends Response<List<Bet>> {
	private List<Bet> bets;

	public void setResponseBody(List<Bet> bets) {
		this.bets = bets;
	}

	public List<Bet> getResponseBody() {
		return bets;
	}
}
