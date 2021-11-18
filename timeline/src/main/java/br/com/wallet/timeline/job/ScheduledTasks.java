package br.com.wallet.timeline.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.wallet.timeline.subscriber.OperationSubscriber;

@Component
public class ScheduledTasks {

	@Autowired
	OperationSubscriber operationReader;
	
	@Value("${wallet.reader.attempts}")
	private Integer attempts;

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(fixedDelay = 200)
	public void scheduleTaskWithFixedDelay() {
		logger.info("Persisting Timeline :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		try {
			TimeUnit.SECONDS.sleep(10);
			for (int i = 0; i < attempts; i++) {
				try {
					operationReader.consumeAndPersistOperationMessage();
				} catch (Exception e) {
					logger.error("Something wrong with operation getting: " + e.getMessage());
				}
			}
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
