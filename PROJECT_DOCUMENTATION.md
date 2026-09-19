# Sentinel AML — Real-Time Money Laundering Detection Platform

## 1. Project Overview

Sentinel AML is a Java Spring Boot based Anti-Money Laundering (AML) transaction monitoring platform.

The system receives financial transactions, evaluates them against configurable AML detection rules, generates alerts for suspicious activity, assigns risk scores, and allows analysts to create and disposition investigation cases.

### Core Flow

Customer → Account → Transaction → AML Detection → Alert → Case → Analyst Disposition

---

## 2. Technology Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* MySQL
* Maven
* REST APIs
* Git / GitHub

---

## 3. System Architecture

```text
                Client / Postman
                       |
                       v
                REST Controllers
                       |
        +--------------+--------------+
        |              |              |
        v              v              v
 Transaction       Alert           Case
 Controller      Controller      Controller
        |
        v
   AlertService
   AML Detection
        |
        v
   Spring Data JPA
        |
        v
      MySQL
```

The `AlertService` acts as the AML detection engine. Whenever a transaction is created, the transaction is passed to the detection engine and evaluated against the implemented AML rules.

---

# 4. Data Model

## Customer

Represents the customer who owns one or more accounts.

Important attributes include:

* Customer ID
* Name
* Date of birth
* Contact information
* Country
* Occupation
* Annual income
* KYC status
* Risk rating
* Politically exposed status

## Account

Represents a bank account belonging to a customer.

Important attributes include:

* Account ID
* Customer
* Account type
* Account status
* Currency
* Open date
* Branch code
* Current balance
* Average monthly balance
* Credit limit
* Account tier

Relationship:

```text
Customer 1 ---- * Account
```

## Transaction

Represents money movement through an account.

Important attributes include:

* Transaction ID
* Account
* Amount
* Currency
* Transaction type
* Transaction time
* Counterparty
* Jurisdiction
* Channel

Relationship:

```text
Account 1 ---- * Transaction
```

## Alert

Represents suspicious activity detected by the AML engine.

Important attributes include:

* Alert ID
* Transaction
* Rule name
* Risk score
* Explanation
* Status
* Created timestamp

## Case

Represents the investigation associated with an alert.

Important attributes include:

* Case ID
* Alert
* Status
* Analyst
* Disposition reason
* Created timestamp
* Updated timestamp

Relationship:

```text
Alert 1 ---- 1 Case
```

---

# 5. AML Detection Rules

The current implementation contains six distinct AML detection rules.

## Rule 1 — Large Transaction Detection

### Logic

A transaction with an amount greater than or equal to 10,000 generates an alert.

```text
Transaction Amount >= 10,000
              |
              v
       LARGE_TRANSACTION
              |
              v
        Risk Score: 70
```

### Alert Explanation

> Transaction amount is greater than or equal to $10,000

---

## Rule 2 — High-Risk Jurisdiction

### Logic

If a transaction is associated with a jurisdiction marked as `HIGH_RISK`, the system generates an alert.

```text
Jurisdiction = HIGH_RISK
        |
        v
HIGH_RISK_JURISDICTION
        |
        v
Risk Score: 90
```

### Alert Explanation

> Transaction involves a high-risk jurisdiction

The current prototype represents jurisdiction risk using values such as `HIGH_RISK` and `LOW_RISK`.

---

## Rule 3 — Structuring / Smurfing

Structuring is a pattern where a larger amount of money may be split into multiple smaller transactions.

### Logic

The system checks for:

* Three or more transactions
* Transactions between 9,000 and 9,999
* Same account
* Within a 24-hour period

```text
Transaction 1 → 9,500
Transaction 2 → 9,600
Transaction 3 → 9,700
       |
       v
Within 24 hours
       |
       v
STRUCTURING
       |
       v
Risk Score: 85
```

### Alert Explanation

> Three or more transactions between 9,000 and 9,999 occurred within 24 hours

This rule has been successfully tested with multiple transactions on the same account.

---

## Rule 4 — Rapid Movement of Funds

This rule identifies situations where a large percentage of deposited funds are transferred out shortly afterward.

### Logic

Within a 48-hour window:

```text
Transferred Amount >= 80% of Deposited Amount
```

Example:

```text
Deposit    = 10,000
Transfer   = 8,500

8,500 / 10,000 = 85%

85% >= 80%
       |
       v
RAPID_MOVEMENT
       |
       v
Risk Score: 88
```

### Alert Explanation

> At least 80% of deposited funds were transferred out within 48 hours

---

## Rule 5 — Behavioral Deviation

This rule identifies transactions that are unusually large compared with the customer's historical transaction behavior.

### Current Prototype Logic

The system calculates the average amount of previous transactions for the same customer.

```text
Historical Average × 3
          |
          v
Current Transaction > 3 × Average
          |
          v
BEHAVIORAL_DEVIATION
          |
          v
Risk Score: 80
```

Example:

```text
Historical transactions:

2,000
3,000
4,000

Average = 3,000

New transaction = 10,000

10,000 > 3 × 3,000

Therefore:
BEHAVIORAL_DEVIATION
```

### Alert Explanation

> Transaction amount is more than 3 times the customer's historical average

### Prototype Limitation

The current implementation uses the historical transaction amount average.

A full production implementation can extend this to calculate the specified 90-day rolling daily customer volume/value.

---

## Rule 6 — Just-Below-Threshold Detection

This rule identifies transactions that are unusually close to, but below, the 10,000 threshold.

### Current Logic

```text
Amount >= 9,500
AND
Amount < 10,000
```

Example:

```text
9,999
   |
   v
JUST_BELOW_THRESHOLD
   |
   v
Risk Score: 75
```

### Alert Explanation

> Transaction amount is unusually close to the 10,000 reporting threshold

A transaction may trigger more than one AML rule. For example, a 9,999 transaction can satisfy both the Structuring and Just-Below-Threshold conditions.

---

# 6. Risk Scores

The current rules use the following risk scores:

| Rule                   | Risk Score |
| ---------------------- | ---------: |
| Large Transaction      |         70 |
| High-Risk Jurisdiction |         90 |
| Structuring            |         85 |
| Rapid Movement         |         88 |
| Behavioral Deviation   |         80 |
| Just-Below-Threshold   |         75 |

These scores are currently assigned directly by the detection rules.

---

# 7. REST APIs

## Transaction API

### Create Transaction

```http
POST /api/v1/transactions
```

Parameters include:

* `accountId`
* `amount`
* `currency`
* `transactionType`
* `jurisdiction`

When a transaction is created:

```text
Request
  |
  v
TransactionController
  |
  v
Save Transaction
  |
  v
AlertService.detect()
  |
  v
AML Rules
  |
  v
Alert
```

---

## Alert API

### Get Alerts

```http
GET /api/v1/alerts
```

Returns the alerts generated by the AML detection engine.

---

## Case API

### Create Case

```http
POST /api/v1/cases
```

Parameters:

* `alertId`
* `analyst`

A case is created with an initial `OPEN` status.

### Disposition Case

```http
PUT /api/v1/cases/{id}/disposition
```

Parameters:

* `status`
* `reason`

This allows an analyst to record the investigation outcome.

### Get Cases

```http
GET /api/v1/cases
```

Returns the available investigation cases.

---

# 8. End-to-End Example

Consider three transactions:

```text
Transaction 1 → ₹9,500
Transaction 2 → ₹9,600
Transaction 3 → ₹9,700
```

All three belong to the same account and occur within 24 hours.

The system processes each transaction.

```text
                Transaction
                     |
                     v
             TransactionController
                     |
                     v
              Save to MySQL
                     |
                     v
              AlertService
                     |
       +-------------+-------------+
       |             |             |
       v             v             v
   Large?       High Risk?    Structuring?
                                  |
                                  v
                              3+ matches
                                  |
                                  v
                              Alert
                                  |
                                  v
                           Risk Score 85
```

An analyst can then create a case:

```text
Alert
  |
  v
Case
  |
  v
Analyst Investigation
  |
  v
Disposition
```

---

# 9. Project Package Structure

```text
src/main/java/com/azenio/hackathon
│
├── config
│   └── SecurityConfig.java
│
├── controller
│   ├── AlertController.java
│   ├── CaseController.java
│   └── TransactionController.java
│
├── entity
│   ├── Account.java
│   ├── Alert.java
│   ├── Case.java
│   ├── Customer.java
│   └── Transaction.java
│
├── repository
│   ├── AccountRepository.java
│   ├── AlertRepository.java
│   ├── CaseRepository.java
│   └── TransactionRepository.java
│
└── service
    └── AlertService.java
```

---

# 10. Security

The project currently contains a Spring Security configuration.

CSRF is disabled for the REST API and endpoints are permitted for the current hackathon prototype.

For a production implementation, authentication and role-based authorization should be added for roles such as:

```text
ADMIN
ANALYST
INVESTIGATOR
```

---

# 11. Database

The application uses MySQL.

Database:

```text
sentinel_aml
```

Spring Data JPA is used to persist entities.

The main relationships are:

```text
Customer
   |
   +---- Account
            |
            +---- Transaction
                     |
                     +---- Alert
                              |
                              +---- Case
```

---

# 12. How to Run the Project

## Requirements

* Java 17+
* MySQL
* Maven or Maven Wrapper

## Start the database

Create the database:

```sql
CREATE DATABASE sentinel_aml;
```

## Configure the application

Configure the MySQL connection in:

```text
src/main/resources/application.properties
```

Do not commit production credentials or passwords to GitHub.

## Run the application

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

# 13. Demo Flow

A hackathon demonstration can follow this sequence:

### Step 1 — Create/prepare customer and account

```text
Customer
   ↓
Account
```

### Step 2 — Submit transactions

```text
POST /api/v1/transactions
```

### Step 3 — AML engine evaluates the transaction

```text
Large Transaction
High-Risk Jurisdiction
Structuring
Rapid Movement
Behavioral Deviation
Just-Below-Threshold
```

### Step 4 — Alert generated

```text
Rule
Risk Score
Explanation
Status
```

### Step 5 — Create investigation case

```text
POST /api/v1/cases
```

### Step 6 — Analyst dispositions the case

```text
PUT /api/v1/cases/{id}/disposition
```

---

# 14. Current Implementation Status

### Implemented

* Customer data model
* Account data model
* Transaction data model
* Alert data model
* Case data model
* REST transaction API
* REST alert API
* REST case API
* MySQL persistence
* Spring Data JPA
* Spring Security configuration
* Large transaction detection
* High-risk jurisdiction detection
* Structuring detection
* Rapid movement detection
* Behavioral deviation detection
* Just-below-threshold detection
* Risk scores
* Alert explanations
* Case creation
* Case disposition
* GitHub source repository

### Future Improvements

Potential enhancements include:

* True 90-day rolling behavioral calculations
* Alert deduplication and aggregation
* Combined risk-score calculation
* Configurable AML rules without code deployment
* Currency normalization and exchange-rate handling
* CSV/batch transaction ingestion
* PII masking
* Role-based authorization
* Immutable audit history
* Swagger/OpenAPI documentation
* More comprehensive JUnit/Mockito tests
* Dashboard for alerts and cases
* Rule versioning
* Kafka/streaming integration

---

# 15. Project Summary

Sentinel AML demonstrates a rule-based AML transaction monitoring workflow using Spring Boot and MySQL.

The system takes transaction data, evaluates multiple suspicious activity patterns, generates risk-scored alerts with explanations, and provides case-management APIs for analyst investigation and disposition.

The primary workflow is:

```text
Customer
    ↓
Account
    ↓
Transaction
    ↓
AML Detection Engine
    ↓
Rule Trigger
    ↓
Risk-Scored Alert
    ↓
Investigation Case
    ↓
Analyst Disposition
```

This provides the foundation for a real-time AML monitoring platform that can be extended with configurable rules, streaming ingestion, advanced behavioral analysis, and production-grade security.
