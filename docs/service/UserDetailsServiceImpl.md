# UserDetailsServiceImpl.java

## 📍 File Location

`src/main/java/org/dnyanyog/service/UserDetailsServiceImpl.java`

---

## 🎯 Purpose

`UserDetailsServiceImpl` is a **core Spring Security integration class**. Its primary responsibility is to:

* Load user information from the database
* Convert persisted `User` data into a **Spring Security–understandable form** (`UserDetails`)

Spring Security uses this service **during authentication and JWT validation**.

---

## 🧱 Base Configuration

* Annotated with `@Service`
* Implements `UserDetailsService` (mandatory for Spring Security)
* Marked `@Transactional` to ensure DB session consistency

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
```

---

## 🔗 Dependencies

| Dependency           | Type            | Responsibility             |
| -------------------- | --------------- | -------------------------- |
| `UserRepository`     | JPA Repository  | Fetches user from database |
| `User`               | Entity          | Implements `UserDetails`   |
| `UserDetailsService` | Spring Security | Authentication contract    |

Injected using Spring’s dependency injection.

---

## 🔐 Method: loadUserByUsername(String usernameOrEmail)

### Purpose

Loads a user by **username OR email**, making login flexible.

Spring Security automatically calls this method when:

* User logs in (`AuthenticationManager.authenticate`)
* JWT filter validates token and sets security context

---

## 🔄 Execution Flow

1. Spring Security receives login request
2. `AuthenticationManager` delegates to `UserDetailsService`
3. `loadUserByUsername()` is invoked
4. User is fetched from database
5. User is returned as `UserDetails`
6. Password & authorities are verified internally

```java
User user = userRepository
    .findByUsernameOrEmail(username, username)
    .orElseThrow(() -> new UsernameNotFoundException(...));
```

---

## 📦 Why Return `User` Directly?

Your `User` entity **implements `UserDetails`**, which allows:

* Zero mapping overhead
* Cleaner authentication logic
* Direct access to roles & flags (`enabled`, `authorities`)

This is a **recommended pattern** for small-to-medium systems.

---

## ⚠️ Exception Handling

| Exception                   | Trigger              |
| --------------------------- | -------------------- |
| `UsernameNotFoundException` | User not found in DB |

Handled by Spring Security and translated into:

* HTTP `401 Unauthorized`
* JSON error response via `AuthenticationEntryPoint`

---

## 🧠 Design Decisions

* **Username OR Email login** → better UX
* **Transactional read** → prevents lazy loading issues
* **No business logic here** → single responsibility

---

## 🔐 Security Considerations

✔ Do not expose whether username or email exists
✔ Avoid logging sensitive identifiers
✔ Ensure `User.isEnabled()` is respected

(Spring Security checks account status automatically)

---

## 🔄 Interaction Diagram

AuthController → AuthService → AuthenticationManager
↓
UserDetailsServiceImpl → DB

JwtAuthenticationFilter → UserDetailsServiceImpl → DB

---

## 🚀 Future Enhancements

* Cache users (Redis / Caffeine)
* Support phone-number login
* Multi-tenant user resolution

---

## ✅ Summary

`UserDetailsServiceImpl` is the **bridge between your database and Spring Security**.

It ensures:

* Secure authentication
* Clean separation of concerns
* Reusable user resolution for both login & JWT validation

This class is small by design—but **critical by responsibility**.
