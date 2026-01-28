# ApiResponse.java

## 📍 File Location

`src/main/java/org/dnyanyog/dto/ApiResponse.java`

---

## 🎯 Purpose

`ApiResponse` is a **generic response wrapper** used across all REST APIs in the project. It standardizes API responses with:

* A success flag
* A message
* Optional data payload

This ensures a **consistent API contract** for both frontend and backend.

---

## 🧱 Base Configuration

* Annotated with Lombok `@Data` → generates getters, setters, `toString()`, `equals()`, `hashCode()`
* Annotated with Lombok `@AllArgsConstructor` → all-args constructor
* Static factory methods `success` and `error` for convenience

```java
@Data
@AllArgsConstructor
public class ApiResponse {
```

---

## 📦 Fields Overview

| Field     | Type      | Purpose                                 |
| --------- | --------- | --------------------------------------- |
| `success` | `boolean` | Indicates if the API call succeeded     |
| `message` | `String`  | Human-readable message                  |
| `data`    | `Object`  | Payload for API responses (can be null) |

---

## ⚡ Static Factory Methods

### 1️⃣ `success(String message, Object data)`

* Returns a **successful response**
* Sets `success = true`
* Populates message and data

### 2️⃣ `error(String message)`

* Returns an **error response**
* Sets `success = false`
* Sets message, data is `null`

**Benefit:** avoids repetitive constructors in controllers.

---

## 🔄 Usage Flow

```java
return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));
return ResponseEntity.badRequest().body(ApiResponse.error("Invalid credentials"));
```

* Used in controllers and global exception handlers
* Provides consistent JSON structure for API clients

---

## 🧠 Design Considerations

* **Generic Object type for data** → flexible but may require casting
* **Success/Error pattern** → simplifies frontend handling
* Could be extended with **error codes** or **timestamp** fields in future

---

## ⚠️ Notes / Best Practices

* Avoid returning entities directly; prefer DTOs
* Keep `message` user-friendly
* Consider adding **HTTP status** if needed for richer API feedback

---

## ✅ Summary

`ApiResponse` is a **lightweight, reusable response wrapper** that ensures all API endpoints return a **consistent and predictable structure**, simplifying both frontend integration and backend maintenance.
