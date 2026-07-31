# 💳 Payment Gateway Simulator

A secure backend application built using **Spring Boot** that simulates the lifecycle of an online payment gateway. The project demonstrates payment processing, JWT authentication, validation, exception handling, and RESTful API development without integrating a real payment provider.

---

<h2 align="center">📸 Project Preview</h2>

<p align="center">
  <img src="readme-preview.png" alt="Payment Gateway Simulator" width="1000"/>
</p>

---
## Features

- User Authentication using Spring Security & JWT
- Create a Payment
- Fetch Payment by ID
- Fetch All Payments
- Update Payment Status
- Delete Payment
- DTO Validation
- Global Exception Handling
- Transaction ID Generation
- RESTful APIs
- MySQL Database Integration
- Docker Compose for MySQL

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- Maven

### Database
- MySQL

### Security
- JWT Authentication
- BCrypt Password Encoder

### Tools
- IntelliJ IDEA
- Postman
- Docker
- Git
- GitHub

---

## 📂 Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── mapper
 ├── exception
 ├── config
 ├── util
 └── enums
```

---

## 📌 API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /payments | Create Payment |
| GET | /payments | Get All Payments |
| GET | /payments/{id} | Get Payment by ID |
| PUT | /payments/{id} | Update Payment |
| DELETE | /payments/{id} | Delete Payment |

---

## 🔒 Security

- JWT Authentication
- Password Encryption using BCrypt
- Protected REST APIs
- Stateless Authentication

---

## 🗄️ Database

- MySQL
- Spring Data JPA
- Hibernate ORM

---

