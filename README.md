# 🔐 Auth Service – Spring Boot JWT Authentication

A simple, production-ready authentication microservice built with  **Spring Boot 3, Spring Security, JWT, and MySQL**. This service provides secure user registration, login, and token-based authentication with proper error handling.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📌 Tech Stack

| Layer      | Technology                  |
| ---------- | --------------------------- |
| Language   | Java 17                     |
| Framework  | Spring Boot 3.1.5           |
| Security   | Spring Security + JWT       |
| ORM        | Spring Data JPA (Hibernate) |
| Database   | MySQL                       |
| Build Tool | Maven                       |
| Validation | Jakarta Validation          |
| Utilities  | Lombok                      |

## ✨ Features

- ✅ **User Registration** with validation
- ✅ **JWT-based Authentication** (24-hour tokens)
- ✅ **Password Encryption** using BCrypt
- ✅ **Role-based Authorization** (USER, ADMIN)
- ✅ **RESTful API** with proper HTTP status codes
- ✅ **Comprehensive Error Handling** with JSON responses
- ✅ **MySQL Database** integration with Hibernate

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or Docker)
- Postman (for API testing)

###

1. **Clone the repository:**
```bash
git clone https://github.com/saurabhsawant1691/auth-service.git
cd auth-service
```

2. **Import the project in your IDE** (e.g., IntelliJ, Eclipse).

3. **Configure MySQL database:**
   - Create a database named `auth_service`.
   - Update `src/main/resources/application.properties` with your DB credentials.

4. **Build and run the application:**
```bash
./mvnw clean spring-boot:run
```

5. **Access the service:**
   - URL: `http://localhost:8080/api/auth`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

6. **Run Postman collection:**
   - Import `Postman_Collection.json` from the project root.

## 📂 Project Structure

The project follows a standard Spring Boot structure:

```
📦 auth-service
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java/org/dnyanyog
│   │   │   ├── AuthServiceMain.java  # Main entry point
│   │   │   ├── 📁 config
│   │   │   │   ├── JwtAuthenticationFilter.java  # JWT filter
│   │   │   │   └── SecurityConfig.java  # Security configuration
│   │   │   ├── 📁 controller
│   │   │   │   └── AuthController.java  # Handles authentication APIs
│   │   │   ├── 📁 dto
│   │   │   │   ├── ApiResponse.java  # Generic API response
│   │   │   │   ├── JwtResponse.java  # JWT token response
│   │   │   │   ├── LoginRequest.java  # Login payload
│   │   │   │   └── SignupRequest.java  # Signup payload
│   │   │   ├── 📁 entity
│   │   │   │   └── User.java  # User entity
│   │   │   ├── 📁 exception
│   │   │   │   ├── GlobalExceptionHandler.java  # Handles exceptions globally
│   │   │   │   └── UserAlreadyExistsException.java  # Custom exception
│   │   │   ├── 📁 repository
│   │   │   │   └── UserRepository.java  # Database operations
│   │   │   ├── 📁 service
│   │   │   │   ├── AuthService.java  # Authentication logic
│   │   │   │   ├── JwtService.java  # JWT management
│   │   │   │   └── UserDetailsServiceImpl.java  # User details for security
│   │   └── 📁 resources
│   │       └── application.properties  # Configuration file
│   └── 📁 test
│       ├── 📁 java  # Test classes
│       └── 📁 resources  # Test resources
├── 📄 pom.xml  # Maven configuration
└── 📄 README.md  # Project documentation
```

## 🧭 Application Flow (High Level)

1. User registers via `/api/auth/signup`
2. User logs in via `/api/auth/login`
3. JWT token is generated and returned
4. Client sends JWT in `Authorization: Bearer <token>` header
5. `JwtAuthenticationFilter` validates token
6. Spring Security authorizes request


## 🔐 Security Architecture

* Stateless authentication using JWT
* Custom `JwtAuthenticationFilter`
* BCrypt password hashing
* Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)
* Custom JSON error responses


## 🧩 Module Documentation Index

### 🚀 Main Application
* [AuthServiceMain](docs/AuthServiceMain.md) - Main application class

### ⚙️ Configuration

* [SecurityConfig](docs/config/SecurityConfig.md) - Spring Security setup
* [JwtAuthenticationFilter](docs/config/JwtAuthenticationFilter.md) - JWT request filter

### 🧩 Controllers

* [AuthController](docs/controller/AuthController.md) - Handles authentication endpoints

### 🧠 Services

* [AuthService](docs/service/AuthService.md) - Core authentication logic
* [JwtService](docs/service/JwtService.md) - JWT token generation and validation
* [UserDetailsServiceImpl](docs/service/UserDetailsServiceImpl.md) - Loads user details for Spring Security

### 📦 Data Transfer Objects (DTOs)

* [ApiResponse](docs/dto/ApiResponse.md) - Standard API response format
* [JwtResponse](docs/dto/JwtResponse.md) - JWT token response
* [LoginRequest](docs/dto/LoginRequest.md) - Login request payload
* [SignupRequest](docs/dto/SignupRequest.md) - Signup request payload

### 🏛️ Entities

* [User](docs/entity/User.md) - User entity mapping to database

### 🗄️ Repositories 

* [UserRepository](docs/repository/UserRepository.md) - JPA repository for User entity

### 🚨 Exception Handling

* [Global Exception Handler](docs/exception/GlobalExceptionHandler.md) - Centralized exception handling
* [UserAlreadyExistsException](docs/exception/UserAlreadyExistsException.md) - Custom exception for duplicate users


### ⚙️ Configuration Files

* [application.properties](docs/application.properties.md) - Application configuration
* [pom.xml](docs/pom.md) - Maven build file

Each file above is **fully documented** with:

* File location
* Purpose
* Core logic
* Flow explanation
* Best practices


## 🌐 API Endpoints

| Method | Endpoint                         | Description         | Auth |
| ------ | -------------------------------- | ------------------- | ---- |
| POST   | `/api/auth/signup`               | Register new user   | ❌    |
| POST   | `/api/auth/login`                | Authenticate user   | ❌    |
| GET    | `/api/check-username/{username}` | Check username      | ✅    |
| GET    | `/api/check-email/{email}`       | Check email         | ✅    |
| ANY    | Other APIs                       | Protected resources | ✅    |


## ⚙️ Configuration Summary

* Server Port: `8080`
* JWT Expiry: `24 hours`
* Password Encoder: `BCrypt`
* Session Policy: `STATELESS`

## 🧠 Design Philosophy

This project follows:

* Clean architecture
* Separation of concerns
* Stateless security model
* Scalable microservice principles

## 👨‍💻 Author

**Saurabh Sawant**
Frontend + Backend Engineer | Fintech Domain

Happy building 🚀