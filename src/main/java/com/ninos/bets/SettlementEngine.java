package com.ninos.bets;

import com.ninos.bets.model.NoSuchBetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SettlementEngine {
	private static Logger logger = LoggerFactory.getLogger(SettlementEngine.class);

	private final ExecutorService executorService = Executors.newCachedThreadPool();
	private final Random random = new SecureRandom();
	private final BetsRepository betsRepository;

	private final Long settlementPeriod;

	@Inject
	public SettlementEngine(
		BetsRepository betsRepository,
		@Value("${bets.settlement.period:1000}") Long settlementPeriod
	) {
		this.betsRepository = betsRepository;
		this.settlementPeriod = settlementPeriod;
	}

	public void schedule(Long betId) {
		executorService.submit(new SettlingTask(betId));
	}

	private class SettlingTask implements Runnable {
		private Long betId;
		private Boolean won;

		public SettlingTask(Long betId) {
			this.betId = betId;
			random.setSeed(betId);
			if (random.nextLong() > Long.MAX_VALUE/2) {
				won = true;
			} else {
				won = false;
			}
		}

		@Override
		public void run() {
			try {
				Thread.sleep(settlementPeriod); // sleep for two minutes
				betsRepository.settle(betId, won);
			} catch (InterruptedException e) {
				logger.error("Sleeping for 2 mins was interrupted: {}. Did not settle bet id: {}", e.getMessage(), betId, e);
			} catch (NoSuchBetException e) {
				logger.error("There is not a bet with id: {}", betId, e);
			}
		}

	}
}
