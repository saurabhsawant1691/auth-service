# LoginRequest.java

## 📍 File Location

`src/main/java/org/dnyanyog/dto/LoginRequest.java`

---

## 🎯 Purpose

`LoginRequest` is a **Data Transfer Object (DTO)** used to capture the **login payload** sent from the frontend. It ensures:

* Only the required fields are accepted
* Input validation is enforced
* Clean separation between API input and internal domain models

This object is used in the authentication endpoint:

```java
@PostMapping("/auth/login")
public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
```

---

## 🧱 Base Configuration

* Annotated with Lombok `@Data` → generates getters, setters, `toString()`, `equals()`, `hashCode()`
* Uses `jakarta.validation.constraints.NotBlank` to enforce required fields

```java
@Data
public class LoginRequest {
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
```

---

## 📦 Fields Overview

| Field             | Type     | Purpose                     | Validation                        |
| ----------------- | -------- | --------------------------- | --------------------------------- |
| `usernameOrEmail` | `String` | Username or email for login | `@NotBlank` ensures it’s provided |
| `password`        | `String` | Password for login          | `@NotBlank` ensures it’s provided |

---

## 🔄 Usage Flow

1. Frontend sends POST request to `/api/auth/login` with JSON payload

```json
{
  "usernameOrEmail": "user@example.com",
  "password": "password123"
}
```

2. Spring MVC binds JSON to `LoginRequest`
3. `@Valid` triggers field validation
4. DTO is passed to `AuthService.authenticateUser()`

---

## 🧠 Design Considerations

* Using DTO prevents **direct exposure of entity objects**
* `NotBlank` ensures **server-side validation** before service logic
* `usernameOrEmail` supports **flexible login** by username or email
* Simple structure keeps validation and serialization straightforward

---

## ⚠️ Notes / Best Practices

* Always annotate with `@Valid` in controllers to enforce validation
* Avoid adding business logic in DTOs
* Consider extending with fields like `rememberMe` or `captcha` in future
* Never include sensitive fields like passwords in logs or responses

---

## ✅ Summary

`LoginRequest` is a **lightweight, validated DTO** that captures user login data safely and clearly. It ensures:

* Clean separation of concerns
* Input validation at the API layer
* Flexible login support (username/email)

This makes the authentication flow **secure, maintainable, and easy to understand**.
