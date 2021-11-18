package br.com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wallet.model.Operation;
import br.com.wallet.repository.OperationRepository;

@RestController
@RequestMapping("/operation")
public class OperationController {
	
    @Autowired
    OperationRepository operationRepository;

	@GetMapping ({ "/timeline/{accountId}" })
	public List<Operation> listTimeLine(@PathVariable(value = "accountId") Integer accountId) {
		return operationRepository.findByAccountId(accountId);
	}	
	
}
