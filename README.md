# PayCart Backend (Mini OMS + Wallet System)

**Enterprise-style Order Management & Wallet System (Microservices)**

PayCart is a production-oriented backend system inspired by real-world e-commerce and payment platforms.
It demonstrates clean architecture, transactional safety, idempotent payments, event-driven communication, and API documentation using Java & Spring Boot.

**Microservices:**

- **Wallet Service** – Manages user wallets, balance, transactions, and idempotent payments
- **Order Service** – Manages products, inventory, orders, and payment orchestration
- **Kafka** – Event-driven communication for payment completion
- **PostgreSQL** – Separate database per service
- **Redis** – Reserved for caching / future enhancements
- **Docker Compose** – Local infrastructure setup

## Project Roadmap

Wallet API: create wallet, top-up, payments
Order API: place order, list orders
Inventory & Product management
JWT authentication
Kafka event publishing & consumption
Integration tests with Testcontainers
Swagger/OpenAPI documentation

## Tech Stack

**Backend:**  
- Java 17  
- Spring Boot 3  
- Spring Web, Spring Security, Spring Data JPA  
- Lombok  

**Databases & Messaging:**  
- PostgreSQL (Wallet DB + Order DB)  
- Redis (idempotency/caching)  
- Apache Kafka  

**DevOps:**  
- Docker & Docker Compose  
- GitHub Actions (coming soon)

## Run Locally

# Start infrastructure
docker compose up -d

# Run services individually
Health endpoints:

Gateway:        http://localhost:8080/health
Wallet Service: http://localhost:8081/health
Order Service:  http://localhost:8082/health

# Sample Flow (End-to-End)
1. Create a wallet
2. Top up wallet
3. Create order
4. Order service:
  - checks inventory
  - debits wallet
  - marks order as PAID
5. Wallet publishes PAYMENT_COMPLETED
6. Order service consumes event

