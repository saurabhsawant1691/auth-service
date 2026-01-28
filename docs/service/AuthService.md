# AuthService.java

## 📍 File Location

`src/main/java/org/dnyanyog/service/AuthService.java`

---

## 🎯 Purpose

`AuthService` contains the **core business logic** for authentication and user registration. It acts as a bridge between:

* Controller layer (REST APIs)
* Security infrastructure (Spring Security + JWT)
* Persistence layer (JPA Repository)

This class is intentionally free from HTTP concerns and focuses purely on **authentication rules and flows**.

---

## 🧱 Base Configuration

* Annotated with `@Service`
* Managed by Spring IoC container
* Stateless (no session data stored)
* Relies on Spring Security context for authentication state

```java
@Service
public class AuthService {
```

---

## 🔗 Dependencies (Injected Components)

| Dependency              | Type            | Responsibility                  |
| ----------------------- | --------------- | ------------------------------- |
| `AuthenticationManager` | Spring Security | Performs credential validation  |
| `UserRepository`        | JPA Repository  | DB operations for `User` entity |
| `PasswordEncoder`       | BCrypt          | Secure password hashing         |
| `JwtService`            | Custom Service  | JWT generation & validation     |

Injected using `@Autowired` (constructor injection can be used later for immutability).

---

## 🔐 Method: authenticateUser(LoginRequest)

### Purpose

Authenticates user credentials and issues a JWT token upon success.

### Flow

1. Accepts username/email and password
2. Delegates authentication to `AuthenticationManager`
3. Stores authentication in `SecurityContextHolder`
4. Extracts authenticated `User` principal
5. Generates JWT token
6. Returns structured `JwtResponse`

```java
Authentication authentication = authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(
        loginRequest.getUsernameOrEmail(),
        loginRequest.getPassword()
    )
);
```

### Why AuthenticationManager?

* Central Spring Security abstraction
* Automatically triggers:

  * `UserDetailsService`
  * `PasswordEncoder`
  * AuthenticationProviders

---

## 🧾 JWT Response Structure

Returned token payload includes:

* Token value
* Token type (`Bearer`)
* User metadata (id, username, email, role)

This avoids extra DB calls on frontend after login.

---

## 🆕 Method: registerUser(SignupRequest)

### Purpose

Registers a new user securely with validation checks.

### Validation Rules

* Username must be unique
* Email must be unique
* Password must be encrypted

```java
if (userRepository.existsByUsername(signupRequest.getUsername())) {
    throw new UserAlreadyExistsException("Username is already taken!");
}
```

### Security Best Practices Applied

* Password encrypted using BCrypt
* Default role assigned (`USER`)
* Account enabled explicitly

---

## ⚠️ Exception Handling

| Exception                    | Thrown When                      |
| ---------------------------- | -------------------------------- |
| `UserAlreadyExistsException` | Username or email already exists |

Handled globally by `GlobalExceptionHandler`.

---

## 🧠 Design Decisions

* **JWT-based authentication** → stateless & scalable
* **SecurityContext usage** → integrates with Spring Security filters
* **Service-layer validation** → keeps controllers thin

---

## 🔄 Interaction Diagram

Controller → AuthService → AuthenticationManager → UserDetailsService → DB
↓
JwtService → Token

---

## 🚀 Future Enhancements

* Role-based signup
* Email verification
* Refresh tokens
* Account lock after failed attempts

---

## ✅ Summary

`AuthService` is the **heart of authentication logic**, cleanly separating:

* API concerns
* Security mechanics
* Persistence rules

It follows **Spring Boot + Security best practices** and is production-ready.
