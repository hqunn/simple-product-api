# Simple Product & Order System API

**Backend Developer Coding Test Submission**

**Candidate:** Dinh Hoang Quan  
**Position:** Backend Developer (LightVision Inc.)

## 📖 Introduction

This project is a RESTful API system designed for managing products and processing orders. It demonstrates transaction management, database modeling, and secure authentication using Spring Boot and PostgreSQL.

The system supports Role-Based Access Control (RBAC), differentiating between Admin (Product management) and Customer (Order placement).

## 🛠 Tech Stack

- **Language:** Java 23
- **Framework:** Spring Boot
- **Database:** PostgreSQL 16 (Containerized via Docker)
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security + JWT (JSON Web Token)
- **Documentation:** Swagger UI
- **Tooling:** Maven, Docker & Docker Compose

## 🚀 Key Features

### 1. Product Management (Admin Only)
- CRUD operations for Products.
- Products are categorized (Electronics, Books, Fashion).
- **Note:** Only Users with `ROLE_ADMIN` can Create or Delete products.

### 2. Order Processing
- **Transactional Integrity:** Uses `@Transactional` to ensure stock is deducted only when an order is successfully placed.
- **Stock Management:** Automatically checks stock availability. If stock is insufficient, the transaction rolls back.
- **Snapshot Pricing:** Stores the product price at the time of purchase in the `order_items` table to prevent historical data inaccuracy.

### 3. Security & Authentication
- **JWT Authentication:** Stateless authentication using Bearer Tokens.
- **BCrypt Encryption:** User passwords are hashed before storage.
- **RBAC:**
  - `ROLE_ADMIN`: Full access to Products and Users.
  - `ROLE_CUSTOMER`: Can view products and place orders.

## 🗂 Database Design

The system relies on a relational database model with 5 tables: `users`, `categories`, `products`, `orders`, and `order_items`.

### 📄 ER Diagram:

A detailed Mermaid definition of the Entity-Relationship Diagram is available in the file `ER_Diagram.mmd` located in the root directory of this repository. You can view it using any Mermaid viewer or the VS Code Mermaid extension.

## ⚙️ Installation & Setup

### Prerequisites
- Docker & Docker Compose (Required for Database)
- Java 17 or higher (Developed on Java 23)
- Maven

### Step-by-Step Guide

#### Clone the repository
```bash
git clone https://github.com/hqunn/simple-product-api.git
cd simple-product-api
```

#### Start the Database (Docker)
This command spins up a PostgreSQL container.
```bash
docker compose up -d
```

#### Run the Application
```bash
mvn spring-boot:run
```

The application will start on port 8080.

## 🧪 Testing the Application

### 1. Default Accounts (Data Seeder)
The application automatically seeds the database with sample data (Categories, Products) and 2 test accounts upon the first run:

| Role | Email | Password | Permissions |
|------|-------|----------|-------------|
| **Admin** | admin@lightvision.com | 123456 | Manage Products, View Users |
| **Customer** | customer@lightvision.com | 123456 | Place Orders, View History |

### 2. API Documentation (Swagger UI)
You can test all APIs interactively via the Swagger UI page:

👉 **http://localhost:8080/swagger-ui/index.html**

### 3. Testing Flow (Recommended)
1. **Login:** Use the `/api/auth/login` endpoint with the credentials above to get a JWT Token.
2. **Authorize:** Click the **Authorize** button in Swagger and paste the token as `Bearer <YOUR_TOKEN>`.
3. **Place Order:** Use the `/api/orders` endpoint to test transaction logic (Try buying more than available stock to see the rollback).

## 📡 API Endpoints Summary

| Module | Method | Endpoint | Access |
|--------|--------|----------|--------|
| **Auth** | POST | `/api/auth/login` | Public |
| | POST | `/api/auth/register` | Public |
| **Products** | GET | `/api/products` | Public |
| | POST | `/api/products` | Admin |
| | DELETE | `/api/products/{id}` | Admin |
| **Orders** | GET | `/api/orders` | Authenticated |
| | POST | `/api/orders` | Authenticated |
| **Users** | GET | `/api/users` | Admin |

## 📬 Contact

**Dinh Hoang Quan (Louis)**  
Email: [louisdinh2107@gmail.com]  
GitHub: [[My GitHub Profile URL](https://github.com/hqunn)]

