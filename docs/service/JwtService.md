# JwtService.java

## 📍 File Location

`src/main/java/org/dnyanyog/service/JwtService.java`

---

## 🎯 Purpose

`JwtService` is responsible for **all JWT (JSON Web Token) operations** in the application. It encapsulates:

* Token generation
* Token parsing
* Claim extraction
* Token validation

This class ensures authentication remains **stateless**, secure, and scalable.

---

## 🧱 Base Configuration

* Annotated with `@Service`
* Managed by Spring IoC container
* Stateless utility-style service
* Uses **JJWT (io.jsonwebtoken)** library

```java
@Service
public class JwtService {
```

---

## ⚙️ External Configuration (application.properties)

| Property             | Purpose                                   |
| -------------------- | ----------------------------------------- |
| `app.jwt.secret`     | Base64-encoded secret key for signing JWT |
| `app.jwt.expiration` | Token validity duration (milliseconds)    |

```properties
app.jwt.secret=BASE64_SECRET_KEY
app.jwt.expiration=86400000
```

---

## 🔗 Dependencies

| Dependency          | Type            | Responsibility                   |
| ------------------- | --------------- | -------------------------------- |
| `@Value`            | Spring          | Injects JWT configuration        |
| `UserDetails`       | Spring Security | Provides authenticated user data |
| `io.jsonwebtoken.*` | JJWT            | JWT creation, parsing, signing   |

No database or HTTP dependencies → clean separation of concerns.

---

## 🔐 Method: generateToken(UserDetails)

### Purpose

Generates a signed JWT token for an authenticated user.

### Token Contents

* **Subject** → username
* **Issued At** → current timestamp
* **Expiration** → now + configured duration
* **Signature** → HMAC-SHA256

```java
.setSubject(userDetails.getUsername())
.setIssuedAt(new Date())
.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
```

---

## 🧩 Method: generateToken(extraClaims, UserDetails)

### Purpose

Allows adding **custom claims** (future extensibility).

### Example Use Cases

* Role-based authorization
* Tenant ID
* User metadata

Currently unused, but **architecturally forward-thinking**.

---

## 🔍 Method: extractUsername(String token)

### Purpose

Extracts the `subject` (username) from JWT.

### Used In

* `JwtAuthenticationFilter`
* Token validation logic

---

## 📤 Method: extractClaim(token, resolver)

### Purpose

Generic utility to extract **any claim** using a resolver function.

### Design Benefit

* Reusable
* Type-safe
* Avoids duplicated parsing logic

---

## ⏳ Method: isTokenValid(token, userDetails)

### Validation Rules

1. Username in token matches authenticated user
2. Token is not expired

```java
return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
```

---

## ⌛ Token Expiry Handling

### Internal Methods

* `extractExpiration()`
* `isTokenExpired()`

Tokens are invalidated **purely by time**, not server state → stateless security.

---

## 🔑 Signing Key Management

### Key Generation Flow

1. Read Base64 secret from config
2. Decode to byte array
3. Generate HMAC key

```java
byte[] keyBytes = Decoders.BASE64.decode(secretKey);
return Keys.hmacShaKeyFor(keyBytes);
```

### Why Base64?

* Required by JJWT
* Ensures key length correctness
* Avoids weak secrets

---

## 🧠 Design Decisions

* **HS256 algorithm** → fast, symmetric signing
* **Stateless JWT** → no DB or cache dependency
* **Centralized token logic** → easy maintenance

---

## ⚠️ Security Considerations

✔ Secret key must be **long & random** (≥256 bits)
✔ Never commit secret to Git
✔ Use env variables in production
✔ Consider refresh tokens for long sessions

---

## 🔄 Interaction Flow

AuthService → JwtService → Token
JwtFilter → JwtService → Claims → SecurityContext

---

## 🚀 Future Enhancements

* Refresh token support
* Role claims inside JWT
* Token revocation (Redis)
* RSA-based signing (RS256)

---

## ✅ Summary

`JwtService` is the **cryptographic backbone** of authentication. It cleanly separates token logic from business and HTTP layers, following Spring Security and JWT best practices.
