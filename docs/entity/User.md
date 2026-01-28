# User.java

## 📍 File Location

`src/main/java/org/dnyanyog/entity/User.java`

---

## 🎯 Purpose

`User` represents the **core identity model** of the application. It serves a **dual role**:

1. **JPA Entity** → persists user data in the database
2. **Spring Security Principal** → represents the authenticated user via `UserDetails`

This design allows the same object to flow seamlessly through **persistence, authentication, and authorization** layers.

---

## 🧱 Base Configuration

* Annotated with `@Entity` → managed by JPA/Hibernate
* Annotated with `@Table` → mapped to `users` table
* Implements `UserDetails` → required by Spring Security
* Uses Lombok for boilerplate reduction

```java
@Entity
@Table(name = "users")
public class User implements UserDetails {
```

---

## 🗄️ Database Mapping Details

### Table Name

`users`

### Unique Constraints

* `username` must be unique
* `email` must be unique

```java
@UniqueConstraint(columnNames = "email"),
@UniqueConstraint(columnNames = "username")
```

This prevents duplicate identities at the **database level**, not just application level.

---

## 📦 Fields Overview

| Field       | Type      | Purpose                           |
| ----------- | --------- | --------------------------------- |
| `id`        | `Long`    | Primary key                       |
| `username`  | `String`  | Login identifier                  |
| `email`     | `String`  | Contact & login identifier        |
| `password`  | `String`  | BCrypt-hashed password            |
| `fullName`  | `String`  | Display name                      |
| `role`      | `String`  | Authorization role (USER / ADMIN) |
| `createdAt` | `Date`    | Record creation timestamp         |
| `updatedAt` | `Date`    | Last update timestamp             |
| `enabled`   | `Boolean` | Account activation flag           |

---

## ⏱️ Auditing (Lifecycle Hooks)

### @PrePersist

Executed before the entity is first saved:

* Sets `createdAt`
* Sets `updatedAt`

### @PreUpdate

Executed before each update:

* Updates `updatedAt`

```java
@PrePersist
protected void onCreated() {
    createdAt = new Date();
    updatedAt = new Date();
}
```

This provides **basic auditing without extra libraries**.

---

## 🔐 Spring Security Integration

### Why Implement `UserDetails`?

Spring Security requires a `UserDetails` object to:

* Validate credentials
* Check account status
* Resolve authorities (roles)

Your `User` entity directly fulfills this contract.

---

## 🛂 Authority Mapping

```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + role)
    );
}
```

### Important Notes

* Spring Security expects roles prefixed with `ROLE_`
* Role stored as simple string for simplicity
* Easy to extend to multi-role later

---

## 🔑 Account Status Methods

| Method                      | Meaning                |
| --------------------------- | ---------------------- |
| `isAccountNonExpired()`     | Account never expires  |
| `isAccountNonLocked()`      | Account is not locked  |
| `isCredentialsNonExpired()` | Password never expires |
| `isEnabled()`               | Controlled via DB flag |

Spring Security automatically enforces these checks.

---

## 🧠 Design Decisions

* **Single-role model** → simpler authorization
* **Entity = Principal** → no mapping overhead
* **Boolean enabled flag** → supports soft deactivation
* **Lifecycle hooks** → lightweight auditing

---

## ⚠️ Security Considerations

✔ Password must always be stored encrypted (BCrypt)
✔ Do NOT expose password in API responses
✔ Avoid serializing this entity directly
✔ Consider DTOs for outward-facing APIs

---

## 🚀 Future Enhancements

* Separate `Role` entity (many-to-many)
* Add `@CreatedDate` / `@LastModifiedDate`
* Soft delete support
* Account lock & expiry fields
* Email verification flag

---

## 🔄 Interaction Flow

Database → UserRepository → UserDetailsServiceImpl →
AuthenticationManager → SecurityContext → JWT

---

## ✅ Summary

`User` is the **foundation of identity and security** in the system.

By combining JPA and Spring Security responsibilities, it enables:

* Clean authentication flow
* Minimal mapping code
* Strong consistency across layers

This is a **pragmatic, production-grade design** for an auth service.
