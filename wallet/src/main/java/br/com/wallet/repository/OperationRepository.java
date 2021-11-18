package br.com.wallet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wallet.model.Operation;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Integer> {
	List<Operation> findByAccountId(Integer accountId);
}