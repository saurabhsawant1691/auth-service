# Spring Boot `pom.xml` – Detailed Explanation & Revision Notes

This document explains the **`pom.xml` file used in a Spring Boot application**. It is written for **revision, onboarding, and future self-review**.

---

## What is `pom.xml`?

`pom.xml` (**Project Object Model**) is the **configuration file for Maven**. It defines:
- Project identity
- Java & Spring Boot version
- Dependencies (libraries)
- Build & packaging rules

Think of it as the **control center** of your Spring Boot application.

---

## 1. Root Project Declaration

```xml
<project
    xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
```

### Purpose
- Declares this file as a **valid Maven POM**
- Enables XML validation by Maven & IDEs

> This is mandatory boilerplate for all Maven projects.

---

## 2. Maven Model Version

```xml
<modelVersion>4.0.0</modelVersion>
```

- Defines the **POM specification version**
- Always `4.0.0` for modern Maven
- **Not related** to Spring Boot version

---

## 3. Spring Boot Parent POM

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.5</version>
</parent>
```

### Why this is critical
The parent POM:
- Manages dependency versions (BOM)
- Configures default Maven plugins
- Sets recommended compiler options
- Ensures compatibility across Spring libraries

### Key Benefit
You **do not need to specify versions** for most dependencies.

---

## 4. Project Coordinates (Identity)

```xml
<groupId>org.dnyanyog</groupId>
<artifactId>auth-service</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>auth-service</name>
```

### Meaning
| Element | Description |
|------|------------|
| groupId | Organization or domain |
| artifactId | Project/module name |
| version | Application version |
| name | Display name |

### Versioning
- `SNAPSHOT` → Active development
- Release example → `1.0.0`

Generated artifact:
```text
auth-service-0.0.1-SNAPSHOT.jar
```

---

## 5. Properties Section

```xml
<properties>
    <java.version>17</java.version>
</properties>
```

### Purpose
- Defines reusable configuration values
- Sets Java version globally

### Notes
- Spring Boot 3.x **requires Java 17+**
- Used by compiler & build plugins internally

---

## 6. Dependencies Section

```xml
<dependencies>
```

This section defines **features available to the application**.

---

### 6.1 Spring Boot Web Starter

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Provides:
- Spring MVC
- Embedded Tomcat
- REST API support
- JSON serialization (Jackson)

Used for building **RESTful services**.

---

### 6.2 Spring Data JPA Starter

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Provides:
- Hibernate ORM
- Spring Data repositories
- Transaction management

Used for:
- `@Entity`
- `JpaRepository`
- Database persistence

---

### 6.3 Spring Security Starter

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Provides:
- Authentication & Authorization
- Security filter chain
- Password encoders
- OAuth2 / JWT support (when configured)

⚠ Adds default security unless overridden.

---

### 6.4 Validation Starter

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Provides:
- Jakarta Bean Validation
- Annotations like:
  - `@NotNull`
  - `@Email`
  - `@Size`

Used mainly for **DTO validation**.

---

## 7. Database Dependency (MySQL)

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Why `runtime` scope?
- Required only when application runs
- Not required during compilation

Spring Boot auto-configures:
- JDBC driver
- Hibernate dialect

---

## 8. Lombok Dependency

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

### Purpose
- Eliminates boilerplate code
- Common annotations:
  - `@Data`
  - `@Builder`
  - `@NoArgsConstructor`

### Why optional?
- Compile-time only
- Not needed by consumers of the JAR

---

## 9. Build Configuration

```xml
<build>
```

Controls how the application is packaged and executed.

---

### 9.1 Spring Boot Maven Plugin

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

### What it does
- Creates executable JAR
- Embeds server (Tomcat)
- Enables `java -jar` execution

Without this plugin, Spring Boot apps cannot run standalone.

---

### 9.2 Excluding Lombok from Final JAR

```xml
<excludes>
    <exclude>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </exclude>
</excludes>
```

### Why exclude Lombok?
- Lombok is compile-time only
- Keeps production artifact clean & smaller

Best practice for production builds.

---

## Final Summary (Quick Revision)

```text
pom.xml defines:
- Who the project is
- Which Java & Spring Boot version it runs on
- What features are enabled
- How the app is built and packaged
```

This file is **mandatory reading for any Spring Boot developer**.

---

## Suggested Next Additions
- Dependency scopes (`compile`, `runtime`, `test`)
- Maven profiles (dev, test, prod)
- Version overriding strategy
- Multi-module Maven setup

---

📌 Keep this file updated whenever dependencies or Java versions change.

