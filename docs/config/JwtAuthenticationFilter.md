# JwtAuthenticationFilter.java – JWT Request Authentication Filter

## File Location

```
src/main/java/org/dnyanyog/config/JwtAuthenticationFilter.java
```

---

## Purpose of this File

`JwtAuthenticationFilter` is responsible for **authenticating every incoming HTTP request using JWT**.

It:

* Extracts JWT from the `Authorization` header
* Validates the token
* Loads user details
* Populates Spring Security’s `SecurityContext`

This filter is the **gatekeeper** of your secured APIs.

---

## Why This File Exists (Design Intent)

Spring Security does **not understand JWT by default**.

This filter:

* Replaces session-based authentication
* Enables stateless security
* Ensures each request is independently authenticated

Without this filter:

> Every secured API would behave as unauthenticated.

---

## Class Declaration

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
```

### `@Component`

* Makes the filter a Spring-managed bean
* Allows injection into `SecurityConfig`

### `OncePerRequestFilter`

* Ensures filter executes **once per request**
* Prevents duplicate authentication processing

---

## Injected Dependencies

```java
@Autowired
private JwtService jwtService;

@Autowired
private UserDetailsServiceImpl userDetailsService;
```

### `JwtService`

* Extracts username from token
* Validates token signature & expiration

### `UserDetailsServiceImpl`

* Loads user details from database
* Provides authorities (roles)

---

## Core Method: `doFilterInternal`

```java
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
```

This method executes **for every incoming HTTP request**.

---

## Step-by-Step Execution Flow

### 1️⃣ Extract JWT Token

```java
String jwt = parseJwt(request);
```

* Reads `Authorization` header
* Expects format: `Bearer <token>`

---

### 2️⃣ Validate Token Presence

```java
if (jwt != null) {
```

* If token missing → request continues unauthenticated
* Security rules decide access

---

### 3️⃣ Extract Username from Token

```java
String username = jwtService.extractUsername(jwt);
```

* Reads JWT claims
* Usually extracts `sub` (subject)

---

### 4️⃣ Prevent Re-authentication

```java
SecurityContextHolder.getContext().getAuthentication() == null
```

* Ensures authentication is set only once
* Prevents overwriting existing context

---

### 5️⃣ Load User Details

```java
UserDetails userDetails = userDetailsService.loadUserByUsername(username);
```

* Fetches user from DB
* Includes roles & authorities

---

### 6️⃣ Validate Token Against User

```java
jwtService.isTokenValid(jwt, userDetails)
```

Checks:

* Token signature
* Token expiration
* Token belongs to user

---

### 7️⃣ Create Authentication Object

```java
UsernamePasswordAuthenticationToken authentication =
    new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
```

* Represents authenticated user
* Credentials are `null` (JWT already verified)

---

### 8️⃣ Attach Request Details

```java
authentication.setDetails(
    new WebAuthenticationDetailsSource().buildDetails(request));
```

Adds:

* IP address
* Session ID (if any)

---

### 9️⃣ Populate Security Context

```java
SecurityContextHolder.getContext().setAuthentication(authentication);
```

This makes the user **authenticated for the remainder of the request**.

---

## Debug Headers (Non-Production)

```java
response.setHeader("X-Token-Valid", "true");
response.setHeader("X-Token-User", username);
```

Used for:

* Debugging
* Postman testing

⚠️ **Should be removed or disabled in production**.

---

## Exception Handling Strategy

The filter gracefully handles JWT-related issues:

| Exception                   | Meaning           | Header Set                         |
| --------------------------- | ----------------- | ---------------------------------- |
| `ExpiredJwtException`       | Token expired     | `X-Token-Error: EXPIRED`           |
| `SignatureException`        | Invalid signature | `X-Token-Error: INVALID_SIGNATURE` |
| `MalformedJwtException`     | Corrupted token   | `X-Token-Error: MALFORMED`         |
| `UsernameNotFoundException` | User not found    | —                                  |
| `IllegalArgumentException`  | Empty claims      | `X-Token-Error: EMPTY_CLAIMS`      |
| `Exception`                 | Unknown error     | `X-Token-Error: UNKNOWN_ERROR`     |

The request **continues**, but without authentication.

---

## Token Missing Case

```java
else {
    response.setHeader("X-Token-Status", "NOT_PROVIDED");
}
```

* No Authorization header
* Request treated as anonymous

---

## Helper Method: `parseJwt`

```java
private String parseJwt(HttpServletRequest request)
```

### Responsibilities

* Extract `Authorization` header
* Validate Bearer format
* Return raw JWT token

---

## Filter Chain Continuation

```java
filterChain.doFilter(request, response);
```

⚠️ **Must always be called**

* Otherwise request will hang

---

## End-to-End Request Flow

```text
HTTP Request
   ↓
JwtAuthenticationFilter
   ↓
JWT Valid?
   ↓ YES
SecurityContext populated
   ↓
Controller / Service Layer
```

---

## Common Mistakes to Avoid

❌ Forgetting to register this filter in `SecurityConfig`

❌ Throwing exceptions instead of letting Spring handle auth failure

❌ Logging sensitive JWT data

❌ Leaving debug headers enabled in production

---

## Revision Checklist

* [ ] Filter extends `OncePerRequestFilter`
* [ ] JWT extracted from Authorization header
* [ ] Token validation implemented
* [ ] SecurityContext populated correctly
* [ ] Filter chain continues

---

## Interview-Level Summary

> `JwtAuthenticationFilter` intercepts every request, validates JWT tokens, loads user details, and establishes authentication in the Spring Security context for stateless authorization.

---

## Next File to Document

👉 **AuthController.java**

Send the file when ready.
