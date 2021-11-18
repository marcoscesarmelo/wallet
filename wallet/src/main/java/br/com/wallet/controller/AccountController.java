package br.com.wallet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wallet.enums.OperationType;
import br.com.wallet.model.Account;
import br.com.wallet.model.Operation;
import br.com.wallet.repository.AccountRepository;
import br.com.wallet.service.SenderService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	SenderService queueSender;

	@PutMapping("/deposit/{accountId}")
	public Account deposit(@PathVariable(value = "accountId") Integer accountId,
			@RequestHeader(value = "amount") Float amount) throws Exception {
		Optional<Account> returnedAccount = accountRepository.findById(accountId);
		Account account = returnedAccount.get();
		try {
			if (account != null && account.deposit(amount)) {
				Account finalAccount = accountRepository.save(account);
				Operation operation = new Operation();
				if (finalAccount != null && operation.buildEvent(accountId, amount, OperationType.DEPOSIT)) {
					queueSender.send(operation);
				}
				return finalAccount;
			} else {
				throw new NotFoundException("Account not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@PutMapping({ "/withdraw/{accountId}" })
	public Account withdrawal(@PathVariable(value = "accountId") Integer accountId,
			@RequestHeader(value = "amount") Float amount) throws Exception {
		Optional<Account> returnedAccount = accountRepository.findById(accountId);
		Account account = returnedAccount.get();
		try {
			if (account != null && account.withdraw(amount)) {
				Account finalAccount = accountRepository.save(account);
				Operation operation = new Operation();
				if (finalAccount != null && operation.buildEvent(accountId, amount, OperationType.WITHDRAW)) {
					queueSender.send(operation);
				}
				return finalAccount;
			} else {
				throw new NotFoundException("Account not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@RequestMapping({ "/payment/{accountId}" })
	public Account payment(@PathVariable(value = "accountId") Integer accountId,
			@RequestHeader(value = "amount") Float amount) throws Exception {
		Optional<Account> returnedAccount = accountRepository.findById(accountId);
		Account account = returnedAccount.get();
		try {
			if (account != null && account.payment(amount)) {
				Account finalAccount = accountRepository.save(account);
				Operation operation = new Operation();
				if (finalAccount != null && operation.buildEvent(accountId, amount, OperationType.PAYMENT)) {
					queueSender.send(operation);
				}
				return finalAccount;
			} else {
				throw new NotFoundException("Account not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@PutMapping({ "/transfer/{accountId}" })
	public Account payment(@PathVariable(value = "accountId") Integer accountId,
			@RequestHeader(value = "amount") Float amount,
			@RequestHeader(value = "destinyAccount") Integer destinyAccountId) throws Exception {
		Optional<Account> returnedAccount = accountRepository.findById(accountId);
		Account account = returnedAccount.get();
		returnedAccount = accountRepository.findById(destinyAccountId);
		Account destinyAccount = returnedAccount.get();
		try {
			if (account != null && destinyAccount != null && (account.transfer(destinyAccount, amount) != null)) {
				Account finalAccount = accountRepository.save(account);
				if (finalAccount == null) {
					throw new Exception("It wasn't possible persist origin account");
				}
				Account finalDestinyAccount = accountRepository.save(destinyAccount);
				if (finalDestinyAccount == null) {
					throw new Exception("It wasn't possible persist destiny account");
				}
				Operation operation = new Operation();
				if (finalAccount != null && finalDestinyAccount != null
						&& operation.buildEvent(accountId, amount, OperationType.TRANSFER)) {
					queueSender.send(operation);
				}
				return finalAccount;
			} else {
				throw new NotFoundException("Account not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
