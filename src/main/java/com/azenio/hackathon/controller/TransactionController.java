package com.azenio.hackathon.controller;

import com.azenio.hackathon.entity.Account;
import com.azenio.hackathon.entity.Transaction;
import com.azenio.hackathon.repository.AccountRepository;
import com.azenio.hackathon.repository.TransactionRepository;
import com.azenio.hackathon.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AlertService alertService;

    public TransactionController(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            AlertService alertService) {

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.alertService = alertService;
    }

    @PostMapping
    public Transaction createTransaction(
            @RequestParam String accountId,
            @RequestParam Double amount,
            @RequestParam String currency,
            @RequestParam String transactionType,
            @RequestParam String jurisdiction) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN-" + System.currentTimeMillis());
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setJurisdiction(jurisdiction);

        Transaction saved = transactionRepository.save(transaction);

        // Run AML detection immediately
        alertService.detect(saved);

        return saved;
    }
}