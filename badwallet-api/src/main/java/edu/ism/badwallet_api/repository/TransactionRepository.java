package edu.ism.badwallet_api.repository;

import edu.ism.badwallet_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}