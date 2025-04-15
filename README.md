# 💸 Cash Desk Module

Java Spring Boot application for managing cash operations (deposits, withdrawals, and balance checks) for multiple cashiers in BGN and EUR currencies.

---

## 🚀 Technologies Used

- Java 17
- Spring Boot
- Maven
- REST API (JSON)
- In-memory logic and `.txt` file storage
- Postman for API testing
- SLF4J for logging

---

## 📦 Features

- Deposits and withdrawals per cashier and currency
- Tracks denominations per operation
- Balance and denominations check (with optional filtering)
- Logs all transactions in `transactions.txt`
- Writes current balances to `balances.txt`
- Secured with a custom API key header

---

## 🧑‍💻 Getting Started

```bash
git clone https://github.com/<your-username>/CashDesk.git
cd CashDesk
mvn spring-boot:run
```

> ⚠️ Requires Java 17 and Maven installed

---

## 🔐 API Key Authentication

All requests must include the following custom header:

```
FIB-X-AUTH: f9Uie8nNf112hx8s
```

---

## 📬 API Endpoints

### 🔹 POST `/api/v1/cash-operation`

Handles both deposits and withdrawals.

#### Sample request:

```json
{
  "cashier": "MARTINA",
  "operationType": "DEPOSIT",
  "currency": "BGN",
  "amount": 600,
  "denominations": {
    "10": 10,
    "50": 10
  }
}
```

---

### 🔹 GET `/api/v1/cash-balance`

Returns current balance and denominations.

#### Optional query parameters:
- `cashier`
- `dateFrom`
- `dateTo`

#### Sample usage:
```
GET /api/v1/cash-balance?cashier=MARTINA
```

---

## 🧪 Testing with Postman

1. Import:
    - `postman/CashDesk.postman_collection.json`
    - `postman/CashDesk.postman_environment.json`
2. Select the environment: `CashDesk Environment`
3. Run the included requests (deposit, withdrawal, balance check)

---

## 📁 Project Structure

```
├── src/main/java/com/fibank/cashdesk/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── config/
│   ├── repository/
│   ├── dto/
│   └── security/
├── src/main/resources/
│   ├── application.properties
│   ├── transactions.txt
│   └── balances.txt
├── postman/
│   ├── CashDesk.postman_collection.json
│   └── CashDesk.postman_environment.json
└── README.md
```

---

## 🧾 Notes

- This project uses **in-memory data structures** and file-based storage (no database)
- All balances and transactions are logged in simple `.txt` files for speed and reliability
- No external APIs or frameworks beyond Spring Boot are used

