package br.com.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wallet.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	Customer findByUsername(String username);
}
