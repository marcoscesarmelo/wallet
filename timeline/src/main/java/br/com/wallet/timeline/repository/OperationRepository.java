package br.com.wallet.timeline.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wallet.timeline.model.Operation;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Integer> {}
