```markdown
# 💳 Payment Gateway Simulator

A full-stack Payment Gateway Simulator inspired by real-world systems like Razorpay and Stripe.  
Simulates payment flows such as initiation, authorization, refunds, and transaction tracking without real money.
---
## 🚀 Features
- Payment processing (Success / Failure / Pending simulation)  
- Refund system (Full & Partial)  
- JWT Authentication & Role-Based Access (User / Merchant / Admin)  
- Transaction history & tracking  
- Webhook simulation for merchant notifications  
- Scalable layered backend architecture  
- Dockerized deployment  
---
## 🏗️ Architecture
```
Client (React)
↓
Controller → Service → Repository → Database (MySQL)

````
---

## 🛠️ Tech Stack
- Java, Spring Boot, Spring Security (JWT)  
- JPA / Hibernate, MySQL  
- React.js  
- Docker, Docker Compose  
- Postman  

---

## ⚙️ Setup
```bash
git clone https://github.com/your-username/payment-gateway-simulator.git
cd payment-gateway-simulator
docker compose up -d
mvn spring-boot:run

# frontend
cd frontend
npm install
npm start
````
---

## 🔄 Payment Flow
```
User → Payment Request → Processing → DB Save → Webhook → Response
```
---
## 📦 APIs
* POST /api/payments
* GET /api/payments/{id}
* POST /api/refunds
* POST /api/auth/login
---

