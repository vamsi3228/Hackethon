package com.azenio.hackathon.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "account_id", nullable = false, length = 50)
    private String accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_status")
    private String accountStatus;

    private String currency;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "current_balance")
    private Double currentBalance;

    @Column(name = "avg_monthly_balance_6m")
    private Double avgMonthlyBalance6m;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "avg_monthly_txn_count")
    private Integer avgMonthlyTxnCount;

    @Column(name = "account_tier")
    private String accountTier;

    // Getters and Setters

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvgMonthlyBalance6m() {
        return avgMonthlyBalance6m;
    }

    public void setAvgMonthlyBalance6m(Double avgMonthlyBalance6m) {
        this.avgMonthlyBalance6m = avgMonthlyBalance6m;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getAvgMonthlyTxnCount() {
        return avgMonthlyTxnCount;
    }

    public void setAvgMonthlyTxnCount(Integer avgMonthlyTxnCount) {
        this.avgMonthlyTxnCount = avgMonthlyTxnCount;
    }

    public String getAccountTier() {
        return accountTier;
    }

    public void setAccountTier(String accountTier) {
        this.accountTier = accountTier;
    }
}