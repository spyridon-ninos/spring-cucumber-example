package com.ninos.bets;

import com.ninos.bets.model.Bet;
import com.ninos.bets.model.NoSuchBetException;
import com.ninos.events.model.NoSuchEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class BetsService {

	private static Logger logger = LoggerFactory.getLogger(BetsService.class);

	private SettlementEngine settlementEngine;
	private BetsRepository betsRepository;

	@Inject
	public BetsService(
		SettlementEngine settlementEngine,
		BetsRepository betsRepository
	) {
		this.settlementEngine = settlementEngine;
		this.betsRepository = betsRepository;
	}

	public List<Bet> getAll() {
		return betsRepository.getHistory();
	}

	public Bet getById(Long id) throws NoSuchBetException {
		return betsRepository.getById(id);
	}

	public Bet save(Bet bet) throws NoSuchEventException {
		Bet savedBet = betsRepository.save(bet);
		settlementEngine.schedule(savedBet.getId());
		return savedBet;
	}

	public Bet cancel(Bet bet) throws NoSuchBetException {
		return betsRepository.cancel(bet.getId());
	}

	public List<Bet> getCancelled() {
		return betsRepository.getCancelled();
	}

	public List<Bet> getSettled() {
		return betsRepository.getSettled();
	}

	public List<Bet> getActive() {
		return betsRepository.getPlaced();
	}
}
