package com.taptica.LottoApi.jobScheduler;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taptica.LottoApi.repository.LottoRepo;

@Component
public class LotteryJob {

	private final ReentrantLock lock = new ReentrantLock();
	private static final Random rand = ThreadLocalRandom.current();

	@Autowired
	private LottoRepo lottoRepo;

	@Scheduled(fixedRate = 1000)
	public void task() {
		Integer lottoRunNum = rand.nextInt(101);
		lock.lock();
		try {
			lottoRepo.printWinningUsers(lottoRunNum);
			lottoRepo.resetPools();
		} finally {
			lock.unlock();
			System.out.println("************ Scheduler complated the job  !!! ***************");
		}
	}

}
