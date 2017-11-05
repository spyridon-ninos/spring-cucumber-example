package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.NoSuchBetException;
import com.ninos.events.model.NoSuchEventException;

import java.util.List;

public interface BetsRepository {
	List<Bet> getPlaced();
	List<Bet> getSettled();
	List<Bet> getCancelled();
	List<Bet> getHistory();
	Bet getById(Long id) throws NoSuchBetException;
	Bet save(Bet bet) throws NoSuchEventException;
	Bet cancel(Long id) throws NoSuchBetException;
	Bet settle(Long id, boolean punterWon) throws NoSuchBetException;
}
