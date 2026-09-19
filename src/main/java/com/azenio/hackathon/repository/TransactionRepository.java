package com.azenio.hackathon.repository;

import com.azenio.hackathon.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}