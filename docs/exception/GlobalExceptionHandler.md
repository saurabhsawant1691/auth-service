# GlobalExceptionHandler.java

## 📍 File Location

`src/main/java/org/dnyanyog/exception/GlobalExceptionHandler.java`

---

## 🎯 Purpose

`GlobalExceptionHandler` is a **centralized exception handler** for the Spring Boot application. It ensures that **all exceptions thrown in the application are captured and converted into meaningful HTTP responses**.

* Provides uniform error response structure
* Handles authentication and authorization errors
* Handles JWT-specific exceptions
* Handles generic exceptions

It is enabled across all controllers using `@RestControllerAdvice`.

---

## 🧱 Base Configuration

* `@RestControllerAdvice` → global controller exception handling
* `@ExceptionHandler(ExceptionType.class)` → method-level exception handling

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {...}
    ...
}
```

---

## 🔄 Exception Handling Flow

| Exception                 | HTTP Status               | Message                                      |
| ------------------------- | ------------------------- | -------------------------------------------- |
| `AuthenticationException` | 401 UNAUTHORIZED          | Standard authentication failure message      |
| `AccessDeniedException`   | 403 FORBIDDEN             | Access denied message                        |
| `ExpiredJwtException`     | 401 UNAUTHORIZED          | "JWT token has expired. Please login again." |
| `SignatureException`      | 401 UNAUTHORIZED          | "JWT signature is invalid"                   |
| `MalformedJwtException`   | 401 UNAUTHORIZED          | "JWT token is malformed"                     |
| `Exception`               | 500 INTERNAL SERVER ERROR | Generic server error message                 |

Steps:

1. Any exception thrown in controller or service layers bubbles up
2. Spring matches it to the appropriate `@ExceptionHandler`
3. A `Map<String, Object>` is constructed with keys: `timestamp`, `status`, `error`, `message`, `path` (if applicable)
4. ResponseEntity is returned with appropriate HTTP status

---

## 🧠 Design Considerations

* Centralizes error handling → **avoid duplicated try-catch** in controllers
* Maintains consistent **JSON response format** for frontend
* Specific JWT exceptions provide clarity on token issues
* Generic `Exception` handler ensures **unhandled exceptions don’t break the API**

---

## ⚠️ Notes / Best Practices

* Always log exceptions (not shown here) for monitoring and debugging
* Avoid sending stack traces directly in response → security risk
* Include path info where possible to help frontend trace errors
* Extend with custom exceptions if needed (e.g., `UserAlreadyExistsException`)

---

## ✅ Summary

`GlobalExceptionHandler` ensures **robust, consistent, and informative error handling** across the auth-service application.

* Handles auth, JWT, and generic errors
* Returns structured JSON response with HTTP status codes
* Makes debugging and frontend integration easier
