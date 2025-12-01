# PayCart Backend (Mini OMS + Wallet System)

PayCart is a simplified, production-ready backend system using a clean microservices architecture.  
It includes:

- **Wallet Service** – UPI-style mini payment system  
- **Order Service** – Product catalog, inventory & order processing  
- **Gateway/Auth Service** – Entry point for all clients  

The project is built showcasing enterprise concepts like microservices, distributed data, idempotent payments, Kafka event processing, and Docker-based local infrastructure.

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
