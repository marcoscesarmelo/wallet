package br.com.wallet.timeline.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import br.com.wallet.timeline.model.Operation;
import br.com.wallet.timeline.writer.OperationWriter;

@Component
public class OperationSubscriber {

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${wallet.rabbitmq.queue}")
	private String queueName;

	@Autowired
	OperationWriter operationWriter;

	public void consumeAndPersistOperationMessage() throws Exception {
		final ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(host);

		final Connection connection = connectionFactory.newConnection();
		final Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);

		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			final String message = new String(delivery.getBody(), "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			Operation operationToPersist = mapper.readValue(message, Operation.class);
			operationWriter.persist(operationToPersist);
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}

}
