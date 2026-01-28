# UserAlreadyExistsException.java

## 📍 File Location

`src/main/java/org/dnyanyog/exception/UserAlreadyExistsException.java`

---

## 🎯 Purpose

`UserAlreadyExistsException` is a **custom runtime exception** used to indicate that a user already exists in the system during registration. It is typically thrown by `AuthService` when a username or email conflict is detected.

This exception ensures that **duplicate user registrations are prevented** and returns a **HTTP 409 Conflict** status.

Example usage in `AuthService.registerUser()`:

```java
if(userRepository.existsByUsername(signupRequest.getUsername())) {
    throw new UserAlreadyExistsException("Username is already taken!");
}
```

---

## 🧱 Base Configuration

* Extends `RuntimeException`
* Annotated with `@ResponseStatus(HttpStatus.CONFLICT)` → automatically maps to **409 Conflict** response

```java
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
```

---

## 🔄 Usage Flow

1. User submits registration request via `SignupRequest`
2. `AuthService.registerUser()` checks for existing username/email
3. If a conflict is found, `UserAlreadyExistsException` is thrown
4. Spring handles the exception and returns **HTTP 409** with message

Example JSON response:

```json
{
  "timestamp": "2026-01-27T00:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Username is already taken!"
}
```

---

## 🧠 Design Considerations

* Custom exceptions improve **readability and maintainability**
* Mapping HTTP status using `@ResponseStatus` avoids manual handling in controllers
* Can be extended for other user-related conflicts in future (e.g., email conflicts)

---

## ⚠️ Notes / Best Practices

* Keep exception messages **clear and concise**
* Avoid exposing sensitive information in messages
* Consider logging exceptions for audit purposes
* Can be used with `GlobalExceptionHandler` for consistent JSON structure

---

## ✅ Summary

`UserAlreadyExistsException` provides a **clear, structured, and maintainable** way to handle duplicate user registrations.

* Ensures conflict is returned with HTTP 409
* Improves readability and separation of concerns
* Integrates seamlessly with Spring Boot exception handling
