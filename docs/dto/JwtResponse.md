# JwtResponse.java

## 📍 File Location

`src/main/java/org/dnyanyog/dto/JwtResponse.java`

---

## 🎯 Purpose

`JwtResponse` is a **Data Transfer Object (DTO)** used to return the **JWT token and user details** to the client after successful authentication. It provides a consistent response structure for frontend applications.

This DTO is returned in the login flow:

```java
@PostMapping("/auth/login")
public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
```

---

## 🧱 Base Configuration

* Annotated with Lombok `@Data` → generates getters, setters, `toString()`, `equals()`, `hashCode()`
* Annotated with Lombok `@AllArgsConstructor` → all-args constructor

```java
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
}
```

---

## 📦 Fields Overview

| Field      | Type     | Purpose                                            |
| ---------- | -------- | -------------------------------------------------- |
| `token`    | `String` | JWT token string                                   |
| `type`     | `String` | Token type (default `Bearer`)                      |
| `id`       | `Long`   | User ID                                            |
| `username` | `String` | Username of the authenticated user                 |
| `email`    | `String` | Email of the authenticated user                    |
| `fullName` | `String` | Full name of the authenticated user                |
| `role`     | `String` | Role of the authenticated user (e.g., USER, ADMIN) |

---

## 🔄 Usage Flow

1. User submits login credentials via `LoginRequest`
2. Authentication succeeds in `AuthService`
3. JWT is generated using `JwtService`
4. `JwtResponse` is populated with:

   * JWT token
   * User details (id, username, email, fullName, role)
   * Token type `Bearer`
5. Returned to frontend inside `ApiResponse.success(...)`

Example JSON response:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGci...",
    "type": "Bearer",
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "role": "USER"
  }
}
```

---

## 🧠 Design Considerations

* Separate DTO from `User` entity to **avoid exposing sensitive data**
* Includes `role` to allow frontend role-based UI adjustments
* Default token type `Bearer` simplifies frontend HTTP Authorization header
* Can be extended in future with fields like `expiresAt` or `permissions`

---

## ⚠️ Notes / Best Practices

* Never include the password or sensitive information in this DTO
* Keep the JWT token secure in the frontend (e.g., HttpOnly cookie or secure storage)
* Ensure token expiration and refresh flow is handled

---

## ✅ Summary

`JwtResponse` is a **lightweight, secure, and structured DTO** for sending authentication results to clients. It ensures:

* Clear separation of concerns
* Only necessary user details are exposed
* Frontend can easily extract token and user info

This makes the authentication flow **safe, maintainable, and easy to integrate**.
