# SecurityConfig.java – Spring Security Configuration

## File Location

```
src/main/java/org/dnyanyog/config/SecurityConfig.java
```

---

## Purpose of this File

`SecurityConfig` is the **core security brain** of the `auth-service`. It defines:

* How users are authenticated
* How passwords are encoded
* Which APIs are public vs secured
* How JWT-based security is enforced
* How security errors are returned (JSON instead of HTML)

In short: **this class controls who can access what and how**.

---

## Why This File Exists (Design Intent)

Spring Security is powerful but strict by default. This configuration:

* Switches from **session-based auth** to **JWT-based stateless auth**
* Replaces default login pages with **REST-friendly JSON responses**
* Integrates custom user loading (`UserDetailsServiceImpl`)

This is mandatory for **modern REST APIs**.

---

## Class-Level Annotations

```java
@Configuration
@EnableWebSecurity
```

### `@Configuration`

* Marks this class as a **Spring configuration source**
* Allows defining `@Bean` methods

### `@EnableWebSecurity`

* Enables Spring Security filter chain
* Activates authentication & authorization mechanisms

---

## Injected Dependencies

```java
@Autowired
private UserDetailsServiceImpl userDetailsServie;

@Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;
```

### `UserDetailsServiceImpl`

* Loads user data from database
* Used during authentication

### `JwtAuthenticationFilter`

* Intercepts requests
* Extracts & validates JWT tokens
* Sets authentication in security context

---

## Password Encoder Bean

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### Why BCrypt?

* One-way hashing
* Salted
* Industry standard

📌 **Never store plain-text passwords**

---

## Authentication Provider

```java
@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsServie);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
}
```

### Role

* Connects:

  * UserDetailsService
  * PasswordEncoder
* Used internally during authentication

This is what verifies:

```text
(username + password) → valid user?
```

---

## AuthenticationManager Bean

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
}
```

### Why this is needed

* Used by controllers/services to authenticate login requests
* Required for manual authentication (e.g. `/login` API)

---

## Custom Authentication Entry Point (401)

```java
@Bean
public AuthenticationEntryPoint authenticationEntryPoint()
```

### Purpose

* Handles **unauthenticated access**
* Returns JSON instead of default HTML error page

### Example Scenario

```text
Request without JWT → 401 Unauthorized
```

### Sample Response

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required",
  "path": "/api/secure"
}
```

---

## Custom Access Denied Handler (403)

```java
@Bean
public AccessDeniedHandler accessDeniedHandler()
```

### Purpose

* Handles **authorized but forbidden access**

### Example Scenario

```text
Valid JWT but insufficient role → 403 Forbidden
```

---

## Security Filter Chain (MOST IMPORTANT PART)

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http)
```

### 1. Disable CSRF

```java
.csrf(csrf -> csrf.disable())
```

* CSRF is for browser-based sessions
* JWT APIs are stateless

---

### 2. Exception Handling

```java
.exceptionHandling(exception -> exception
    .authenticationEntryPoint(authenticationEntryPoint())
    .accessDeniedHandler(accessDeniedHandler())
)
```

Ensures **all security errors return JSON**.

---

### 3. Stateless Session Management

```java
.sessionManagement(session ->
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
)
```

* No HTTP session
* Every request must carry JWT

---

### 4. Authorization Rules

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/test/**").permitAll()
    .anyRequest().authenticated()
)
```

#### Meaning

| Endpoint     | Access                |
| ------------ | --------------------- |
| /api/auth/** | Public (login/signup) |
| /api/test/** | Public                |
| Others       | JWT required          |

---

### 5. Register Authentication Provider

```java
http.authenticationProvider(authenticationProvider());
```

Tells Spring Security **how authentication should be performed**.

---

### 6. JWT Filter Registration

```java
http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```

### Why before UsernamePasswordAuthenticationFilter?

* JWT must be validated **before** Spring tries default auth
* Sets security context early

---

## Request Flow (End-to-End)

```text
Incoming Request
   ↓
JwtAuthenticationFilter
   ↓
Token Valid?
   ↓ YES
SecurityContext populated
   ↓
Controller invoked
```

---

## Common Mistakes to Avoid

❌ Forgetting to permit `/api/auth/**`

❌ Using session-based auth with JWT

❌ Not registering JWT filter

❌ Returning HTML error pages in REST APIs

---

## Revision Checklist

* [ ] JWT filter added before UsernamePasswordAuthenticationFilter
* [ ] Stateless session policy enabled
* [ ] BCrypt password encoder used
* [ ] Public & secured routes defined
* [ ] Custom JSON error handling present

---

## Interview-Level Summary

> `SecurityConfig` defines a stateless JWT-based security architecture, custom authentication handling, and precise authorization rules for REST APIs.

---