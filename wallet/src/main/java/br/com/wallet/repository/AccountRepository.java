package br.com.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wallet.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findById(long id);

}