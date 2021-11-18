package br.com.wallet.timeline.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wallet.timeline.model.Operation;
import br.com.wallet.timeline.repository.OperationRepository;

@Component
public class OperationWriter {

	@Autowired
	OperationRepository operationRepository;
	
	public Operation persist(Operation operation) {
		return operationRepository.save(operation);
	}	
	
}
