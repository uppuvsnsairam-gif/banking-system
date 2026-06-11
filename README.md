# Banking System REST API

A secure banking backend built with Spring Boot, JWT Authentication and MySQL.

## Tech Stack
- Java + Spring Boot 3.5
- Spring Security + JWT Authentication
- MySQL + Spring Data JPA
- REST APIs
- Maven

## Features
- User Registration and Login with JWT
- Create Bank Accounts (Savings or Current)
- Deposit Money
- Withdraw Money
- Fund Transfer between accounts
- Transaction History

## API Endpoints

### Auth APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login and get JWT token |

### Account APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/accounts/create | Create new account |
| GET | /api/accounts/my-accounts | Get my accounts |
| GET | /api/accounts/{accountNumber} | Get account details |

### Transaction APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/transactions/deposit | Deposit money |
| POST | /api/transactions/withdraw | Withdraw money |
| POST | /api/transactions/transfer | Transfer money |
| GET | /api/transactions/history/{accountNumber} | Transaction history |

## How to Run

1. Clone the repository and open in VS Code

2. Create MySQL database: CREATE DATABASE banking_system

3. Update application.properties with your MySQL username and password

4. Run: ./mvnw spring-boot:run

5. Application starts on http://localhost:8080

## Author
Uppu Venkata Surya Narayana Sai Ram
- GitHub: https://github.com/uppuvsnsairam-gif
- Email: uppuvsnsairam@gmail.com