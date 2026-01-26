# 🔐 Spring Boot Authentication Service

A simple, production-ready authentication microservice built with Spring Boot, MySQL, and JWT. This service provides secure user registration, login, and token-based authentication with proper error handling.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ Features

- ✅ **User Registration** with validation
- ✅ **JWT-based Authentication** (24-hour tokens)
- ✅ **Password Encryption** using BCrypt
- ✅ **Role-based Authorization** (USER, ADMIN)
- ✅ **RESTful API** with proper HTTP status codes
- ✅ **Comprehensive Error Handling** with JSON responses
- ✅ **MySQL Database** integration with Hibernate
- ✅ **Docker Support** for easy setup
- ✅ **Postman Collection** for API testing
- ✅ **Production-ready Security** configuration

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or Docker)
- Postman (for API testing)

### Option 1: Using Docker (Recommended)

1. **Clone and setup:**
```bash
git clone https://github.com/saurabhsawant1691/auth-service.git
cd auth-service