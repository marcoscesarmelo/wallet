package br.com.wallet.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.wallet.model.Operation;

@Service
public class SenderService {
	
	@Autowired
	private AmqpTemplate queueTemplate;
	
	@Value("${wallet.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${wallet.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Operation operation) {		
		queueTemplate.convertAndSend(exchange, routingkey, operation);
	}
}