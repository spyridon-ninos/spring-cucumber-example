package com.ninos.bets.integration;

import com.ninos.bets.BetsRepository;
import com.ninos.bets.model.Bet;
import com.ninos.bets.model.BetStatus;
import com.ninos.bets.model.NoSuchBetException;
import com.ninos.events.EventsRepository;
import com.ninos.events.model.NoSuchEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Repository
public final class MapInMemoryBetsRepository implements BetsRepository {

	private static Logger logger = LoggerFactory.getLogger(MapInMemoryBetsRepository.class);

	private final Map<Long, Bet> betMap = new LinkedHashMap<Long, Bet>();
	private final EventsRepository eventsRepository;

	@Inject
	public MapInMemoryBetsRepository(
		EventsRepository eventsRepository
	) {
		this.eventsRepository = eventsRepository;
	}

	public synchronized List<Bet> getPlaced() {
		return betMap
				.values()
				.stream()
				.filter(bet -> BetStatus.PLACED.equals(bet.getStatus()))
				.collect(Collectors.toList());
	}

	public synchronized List<Bet> getSettled() {
		return betMap
				.values()
				.stream()
				.filter(bet -> BetStatus.SETTLED.equals(bet.getStatus()))
				.collect(Collectors.toList());
	}

	public synchronized List<Bet> getCancelled() {
		return betMap
				.values()
				.stream()
				.filter(bet -> BetStatus.CANCELLED.equals(bet.getStatus()))
				.collect(Collectors.toList());
	}

	public synchronized List<Bet> getHistory() {
		return new ArrayList<>(betMap.values());
	}

	public synchronized Bet getById(Long id) throws NoSuchBetException {
		if (betMap.keySet().contains(id)) {
			return betMap.get(id);
		}

		throw new NoSuchBetException("Bet with id " + id + " was not found");
	}

	public synchronized Bet save(Bet bet) throws NoSuchEventException {
		// with a real db, some sort of optimisation should take place here
		if (eventsRepository.getById(bet.getEventId()) == null) {
			throw new IllegalArgumentException("No event with id " + bet.getEventId() + " was found");
		}
		eventsRepository.getById(bet.getEventId());

		Long nextId = getNextId();
		bet.setId(nextId);
		bet.setStatus(BetStatus.PLACED);
		betMap.put(nextId, bet);
		return bet;
	}

	public synchronized Bet cancel(Long id) throws NoSuchBetException {
		Bet bet = getById(id);

		// only cancel bets that are pending settlement
		if (BetStatus.PLACED.equals(bet.getStatus())) {
			bet.setStatus(BetStatus.CANCELLED);
			bet.setAmountRewarded(0.0);
			bet.setWhenStatusChanged(LocalDateTime.now());
		}

		return bet;
	}

	public synchronized Bet settle(Long id, boolean punterWon) throws NoSuchBetException {
		Bet bet = getById(id);

		// we settle bets only if they are stin pending settlement (placed)
		if (BetStatus.PLACED.equals(bet.getStatus())) {
			bet.setStatus(BetStatus.SETTLED);
			bet.setWhenStatusChanged(LocalDateTime.now());
			bet.setPunterWon(punterWon);

			if (punterWon == true) {
				bet.setAmountRewarded(bet.getAmountPlaced() * 2);
			} else {
				bet.setAmountRewarded(0.0);
			}
		}

		return bet;
	}

	private Long getNextId() {
		List<Long> ids = new ArrayList<>(betMap.keySet());

		OptionalLong nextId = ids
				.stream()
				.mapToLong(id -> id)
				.max();

		if (nextId.isPresent()) {
			return nextId.getAsLong() + 1;
		} else {
			return 1L;
		}
	}
}
