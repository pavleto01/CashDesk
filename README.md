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
- Secured with a custom API key header (configurable)
- Global exception handler for readable error responses

---

## 🧑‍💻 Getting Started

```bash
git clone https://github.com/pavleto01/CashDesk.git
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

> Can be configured via `application.properties`:
> ```properties
> security.api-key=f9Uie8nNf112hx8s
> security.api-key-header=FIB-X-AUTH
> ```

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
- `dateFrom` (format: `YYYY-MM-DD`)
- `dateTo` (format: `YYYY-MM-DD`)

#### Sample usage:
```
GET /api/v1/cash-balance?cashier=MARTINA&dateFrom=2025-04-01
```

---

## 🧪 Testing with Postman

1. Import:
   - `postman/CashDesk_Full_Test_Suite.postman_collection.json`
   - `postman/CashDesk.postman_environment.json`
2. Select the environment: `CashDesk Environment`
3. Run the included requests (deposit, withdrawal, balance check, errors)

---

## 📁 Project Structure

```
├── src/main/java/com/fibank/cashdesk/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── config/
│   ├── dto/
│   ├── security/
│   └── exception/
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
- API key and file paths are configurable
- Ready for integration with database layer in the future
