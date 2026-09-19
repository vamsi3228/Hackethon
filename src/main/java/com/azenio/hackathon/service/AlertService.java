package com.azenio.hackathon.service;

import com.azenio.hackathon.entity.Alert;
import com.azenio.hackathon.entity.Transaction;
import com.azenio.hackathon.repository.AlertRepository;
import org.springframework.stereotype.Service;
import com.azenio.hackathon.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final TransactionRepository transactionRepository;

    public AlertService(
            AlertRepository alertRepository,
            TransactionRepository transactionRepository) {

        this.alertRepository = alertRepository;
        this.transactionRepository = transactionRepository;
    }

    public void detect(Transaction transaction) {

        // Rule 1: Large Transaction
        if (transaction.getAmount() >= 10000) {

            Alert alert = new Alert();

            alert.setTransaction(transaction);
            alert.setRuleName("LARGE_TRANSACTION");
            alert.setRiskScore(70);
            alert.setExplanation(
                    "Transaction amount is greater than or equal to $10,000"
            );
            alert.setStatus("OPEN");
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);
        }

        // Rule 2: High Risk Jurisdiction
        if (transaction.getJurisdiction() != null &&
                transaction.getJurisdiction().equalsIgnoreCase("HIGH_RISK")) {

            Alert alert = new Alert();

            alert.setTransaction(transaction);
            alert.setRuleName("HIGH_RISK_JURISDICTION");
            alert.setRiskScore(90);
            alert.setExplanation(
                    "Transaction involves a high-risk jurisdiction"
            );
            alert.setStatus("OPEN");
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);
        }

        // Rule 3: Structuring / Smurfing
        if (transaction.getAmount() >= 9000 &&
                transaction.getAmount() <= 9999) {

            List<Transaction> recentTransactions =
                    transactionRepository.findAll().stream()
                            .filter(t -> t.getAccount().getAccountId()
                                    .equals(transaction.getAccount().getAccountId()))
                            .filter(t -> t.getTransactionTime()
                                    .isAfter(transaction.getTransactionTime().minusHours(24)))
                            .filter(t -> t.getAmount() >= 9000 &&
                                    t.getAmount() <= 9999)
                            .toList();

            if (recentTransactions.size() >= 3) {

                Alert alert = new Alert();

                alert.setTransaction(transaction);
                alert.setRuleName("STRUCTURING");
                alert.setRiskScore(85);
                alert.setExplanation(
                        "Three or more transactions between 9,000 and 9,999 occurred within 24 hours"
                );
                alert.setStatus("OPEN");
                alert.setCreatedAt(LocalDateTime.now());

                alertRepository.save(alert);
            }
        }

        // Rule 4: Rapid Movement of Funds
        if ("TRANSFER".equalsIgnoreCase(transaction.getTransactionType())) {

            List<Transaction> recentTransactions =
                    transactionRepository.findAll().stream()
                            .filter(t -> t.getAccount().getAccountId()
                                    .equals(transaction.getAccount().getAccountId()))
                            .filter(t -> t.getTransactionTime()
                                    .isAfter(transaction.getTransactionTime().minusHours(48)))
                            .toList();

            double depositedAmount = recentTransactions.stream()
                    .filter(t -> "DEPOSIT".equalsIgnoreCase(t.getTransactionType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double transferredAmount = recentTransactions.stream()
                    .filter(t -> "TRANSFER".equalsIgnoreCase(t.getTransactionType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            if (depositedAmount > 0 &&
                    transferredAmount >= depositedAmount * 0.80) {

                Alert alert = new Alert();

                alert.setTransaction(transaction);
                alert.setRuleName("RAPID_MOVEMENT");
                alert.setRiskScore(88);
                alert.setExplanation(
                        "At least 80% of deposited funds were transferred out within 48 hours"
                );
                alert.setStatus("OPEN");
                alert.setCreatedAt(LocalDateTime.now());

                alertRepository.save(alert);
            }
        }
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}