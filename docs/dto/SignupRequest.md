# SignupRequest.java

## 📍 File Location

`src/main/java/org/dnyanyog/dto/SignupRequest.java`

---

## 🎯 Purpose

`SignupRequest` is a **Data Transfer Object (DTO)** used to capture the **user registration payload** from the frontend. It ensures:

* Required fields are present
* Input is validated
* Clean separation between API input and internal domain models

This DTO is used in the registration endpoint:

```java
@PostMapping("/auth/signup")
public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest)
```

---

## 🧱 Base Configuration

* Annotated with Lombok `@Data` → generates getters, setters, `toString()`, `equals()`, `hashCode()`
* Uses `jakarta.validation.constraints` to enforce input rules

```java
@Data
public class SignupRequest {
    @NotBlank ...
    @Size ...
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size ...
    private String password;

    @NotBlank
    private String fullName;
}
```

---

## 📦 Fields Overview

| Field      | Type     | Purpose                      | Validation                            |
| ---------- | -------- | ---------------------------- | ------------------------------------- |
| `username` | `String` | Unique username for the user | Not blank, 3–20 chars                 |
| `email`    | `String` | User email                   | Not blank, must be valid email format |
| `password` | `String` | User password                | Not blank, 6–40 chars                 |
| `fullName` | `String` | User's full name             | Not blank                             |

---

## 🔄 Usage Flow

1. Frontend sends POST request to `/api/auth/signup` with JSON payload

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "secret123",
  "fullName": "John Doe"
}
```

2. Spring MVC binds JSON to `SignupRequest`
3. `@Valid` triggers field validation
4. DTO is passed to `AuthService.registerUser()`
5. User entity is created and persisted if validations pass

---

## 🧠 Design Considerations

* DTO prevents **direct exposure of entity objects**
* Field validation ensures **clean input** before hitting service or repository layer
* Restricting sizes and formats improves **data integrity**
* Separate from `User` entity → avoids coupling persistence with API contract

---

## ⚠️ Notes / Best Practices

* Always use `@Valid` in controller methods
* Avoid business logic inside DTO
* Ensure passwords are **hashed** before storage (handled in service layer)
* Consider adding extra validations like:

  * Password strength
  * Username uniqueness (service layer)

---

## ✅ Summary

`SignupRequest` is a **lightweight, validated DTO** that captures registration data safely and clearly. It ensures:

* Clean separation of concerns
* Input validation at API layer
* Enforces constraints on fields like username, email, and password

This makes the **registration flow robust, maintainable, and secure**.
