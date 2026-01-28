# application.properties

## 📍 File Location

`src/main/resources/application.properties`

---

## 🎯 Purpose

This file contains **centralized configuration** for the Auth Service Spring Boot application. It defines:

* Server configuration
* Active profile
* Database connectivity
* JPA/Hibernate behavior
* JWT security settings
* Logging levels

Spring Boot automatically loads this file at startup.

---

## ⚙️ Server Configuration

```properties
server.port=8080
```

### Explanation

* Runs the application on **port 8080**
* Default port for Spring Boot applications

---

## 🧩 Active Profile

```properties
spring.profiles.active=mysql
```

### Explanation

* Activates the `mysql` profile
* Allows profile-based configuration (`application-mysql.properties` if added later)
* Useful for environment separation (dev / test / prod)

---

## 🗄️ MySQL Database Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_service_db?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Coverdrive@123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Explanation

* Connects to **MySQL database** `auth_service_db`
* Automatically creates database if not present
* Uses MySQL Connector/J driver

⚠️ **Security Note**: Credentials should be moved to environment variables in production.

---

## 🧬 JPA / Hibernate Configuration

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Explanation

* `ddl-auto=update` → Automatically updates schema
* `show-sql=true` → Logs SQL queries
* `format_sql=true` → Pretty-prints SQL
* Dialect optimized for **MySQL 8**

### Best Practice

| Environment | ddl-auto value |
| ----------- | -------------- |
| Local Dev   | update         |
| QA/Test     | validate       |
| Production  | none           |

---

## 🔐 JWT Configuration

```properties
app.jwt.secret=mySecretKeyThatIsAtLeast256BitsLongForHS256Algorithm12345
app.jwt.expiration=86400000
```

### Explanation

* `secret` → Used to sign JWT tokens (HS256)
* Must be **at least 256 bits**
* `expiration` → Token validity in milliseconds

### Token Lifetime

* `86400000 ms` = **24 hours**

### Used In

* `JwtService`
* `JwtAuthenticationFilter`

---

## 📊 Logging Configuration

```properties
logging.level.com.example.auth=DEBUG
```

### Explanation

* Enables DEBUG logs for the auth module
* Useful for tracing authentication and JWT flow

---

## 🔁 Configuration Usage Flow

1. Spring Boot starts
2. Loads `application.properties`
3. Initializes datasource, JPA, security, JWT
4. Beans use `@Value` and auto-configured properties

---

## 🧠 Design Considerations

* Centralized configuration improves maintainability
* Supports profile-based environment separation
* JWT settings are externalized for flexibility

---

## ⚠️ Production Recommendations

* Move secrets to:

  * Environment variables
  * Vault (GCP Secret Manager / AWS Secrets Manager)
* Disable SQL logging
* Use stronger JWT rotation strategy

---

## ✅ Summary

`application.properties` is the **backbone configuration file** of the Auth Service:

* Controls server, DB, ORM, security
* Enables JWT authentication
* Supports scalable environment setup

This file should be reviewed carefully before every deployment.
