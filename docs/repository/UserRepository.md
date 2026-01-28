# UserRepository.java

## 📍 File Location

`src/main/java/org/dnyanyog/repository/UserRepository.java`

---

## 🎯 Purpose

`UserRepository` is the **data access layer** for the `User` entity. It abstracts all database operations related to users and provides clean, intention‑revealing methods used by:

* Authentication flow (login)
* Registration flow (signup)
* Username / email availability checks

By using Spring Data JPA, this repository avoids boilerplate SQL and keeps persistence logic declarative.

---

## 🧱 Base Configuration

* Annotated with `@Repository`
* Extends `JpaRepository<User, Long>`
* Automatically implemented by Spring at runtime

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
```

Extending `JpaRepository` provides:

* CRUD operations
* Pagination & sorting
* Transaction integration

---

## 🔗 Dependencies

| Dependency      | Type            | Responsibility            |
| --------------- | --------------- | ------------------------- |
| `JpaRepository` | Spring Data JPA | Base repository contract  |
| `User`          | Entity          | Target persistence model  |
| `Optional`      | Java            | Null‑safe return handling |

No explicit SQL, EntityManager, or JDBC code is required.

---

## 🔍 Query Methods (Derived Queries)

Spring Data JPA **derives SQL automatically** based on method names.

---

### 1️⃣ `Optional<User> findByUsername(String username)`

**Purpose**
Fetch a user using username.

**Generated SQL (conceptual)**

```sql
SELECT * FROM users WHERE username = ?
```

**Used In**

* Login flow
* Username availability checks

---

### 2️⃣ `Optional<User> findByEmail(String email)`

**Purpose**
Fetch a user using email.

**Generated SQL (conceptual)**

```sql
SELECT * FROM users WHERE email = ?
```

**Used In**

* Email availability checks
* Login using email

---

### 3️⃣ `Boolean existsByUsername(String username)`

**Purpose**
Checks whether a username already exists.

**Why this exists**

* Faster than fetching full entity
* Prevents duplicate registration

**Used In**

* Signup validation

---

### 4️⃣ `Boolean existsByEmail(String email)`

**Purpose**
Checks whether an email is already registered.

**Design Benefit**

* Efficient
* Clean validation logic in service layer

---

### 5️⃣ `Optional<User> findByUsernameOrEmail(String username, String email)`

**Purpose**
Allows authentication using **either username or email**.

**Generated SQL (conceptual)**

```sql
SELECT * FROM users WHERE username = ? OR email = ?
```

**Used In**

* `UserDetailsServiceImpl`
* Login flow

---

## 🔐 Role in Authentication Flow

```
Login Request
   ↓
AuthenticationManager
   ↓
UserDetailsServiceImpl
   ↓
UserRepository.findByUsernameOrEmail()
   ↓
User Entity → SecurityContext → JWT
```

This repository is a **critical dependency** for authentication but contains **no security logic itself**.

---

## 🧠 Design Decisions

* **Optional return type** → avoids `NullPointerException`
* **Derived queries** → readable & maintainable
* **No custom JPQL yet** → keeps repository simple
* **Boolean existence checks** → optimized validation

---

## ⚠️ Common Pitfalls & Notes

⚠️ Avoid calling `findByUsername()` just to check existence
→ use `existsByUsername()` instead

⚠️ Do not add business logic here
→ repositories should stay thin

⚠️ Ensure DB indexes exist on:

* `username`
* `email`

---

## 🚀 Future Enhancements

* Add DB indexes explicitly
* Introduce soft‑delete filtering
* Custom JPQL for complex queries
* Caching frequently accessed users

---

## ✅ Summary

`UserRepository` is a **clean, declarative persistence layer** that:

* Keeps database access simple
* Enables flexible login strategies
* Supports secure signup validation

It follows Spring Data JPA best practices and keeps the rest of the application **database‑agnostic**.
