# AuthController

## File Location

```text
src/main/java/org/dnyanyog/controller/AuthController.java
```

---

## Purpose

`AuthController` is the **API entry point for authentication and user validation**. It exposes REST endpoints for:

* User login (JWT generation)
* User signup (registration)
* Username & email availability checks

This controller does **not** contain business logic. It delegates all core responsibilities to service-layer components, keeping the controller thin and maintainable.

---

## Base Configuration

```java
@RestController
@RequestMapping("/api")
```

* `@RestController`: Marks this class as a REST API controller
* `@RequestMapping("/api")`: All endpoints are prefixed with `/api`

---

## Dependencies

```java
@Autowired
private AuthService authService;

@Autowired
private UserDetailsServiceImpl userDetailsService;
```

### Why these are injected

* **AuthService** → Handles authentication & registration logic
* **UserDetailsServiceImpl** → Used to fetch user details (Spring Security integration)

Controller does not directly talk to DB or security internals.

---

## API Endpoints

### 1️⃣ Login API

```java
@PostMapping("/auth/login")
```

#### Request

* **URL**: `/api/auth/login`
* **Method**: `POST`
* **Body**: `LoginRequest`
* **Validation**: `@Valid`

#### Flow

1. Receives login credentials (username + password)
2. Delegates authentication to `AuthService`
3. On success, receives `JwtResponse`
4. Returns a standardized success response

#### Code Path

```java
JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
```

#### Response

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "<JWT_TOKEN>",
    "type": "Bearer"
  }
}
```

🔐 **JWT is generated in the service layer, not here**

---

### 2️⃣ Signup API

```java
@PostMapping("/auth/signup")
```

#### Request

* **URL**: `/api/auth/signup`
* **Method**: `POST`
* **Body**: `SignupRequest`
* **Validation**: `@Valid`

#### Flow

1. Receives registration details
2. Calls `authService.registerUser()`
3. Persists user (password encrypted)
4. Returns saved user entity

#### Code Path

```java
User user = authService.registerUser(signupRequest);
```

#### Response

```json
{
  "success": true,
  "message": "User registered successfully",
  "data": { "id": 1, "username": "john" }
}
```

⚠️ **In production, avoid returning full User entity**

---

### 3️⃣ Username Availability Check

```java
@GetMapping("/check-username/{username}")
```

#### Purpose

Checks whether a username already exists.

#### Flow

1. Calls `loadUserByUsername(username)`
2. If user exists → returns user details
3. If not → Spring Security throws exception (can be mapped)

#### Code Path

```java
UserDetails user = userDetailsService.loadUserByUsername(username);
```

⚠️ **Current behavior**:

* Uses exception-based existence check
* Prints user info to console (debug only)

🛑 **Production Warning**

* Do NOT log user details
* Replace with explicit repository existence check

---

### 4️⃣ Email Availability Check

```java
@GetMapping("/check-email/{email}")
```

#### Current State

* Stub implementation
* Always returns success

#### Expected Future Implementation

* Check DB for existing email
* Return availability flag

---

## Response Wrapper – ApiResponse

All APIs return a consistent response structure:

```json
{
  "success": true | false,
  "message": "...",
  "data": {}
}
```

### Benefits

* Frontend consistency
* Easier error handling
* Cleaner API contracts

---

## Design Principles Followed

* Thin controller
* Business logic in service layer
* Validation at API boundary
* JWT handled outside controller
* Clean separation of concerns

---

## Revision Summary (Interview Ready)

* AuthController exposes auth-related REST APIs
* Delegates authentication to AuthService
* Uses Spring Security’s UserDetailsService
* Returns JWT via standardized ApiResponse
* Controller never handles passwords or tokens directly

---

## Improvements To Do

* Replace username check logic
* Implement email availability check
* Mask sensitive user data in responses
* Add global exception handling

---

✅ This controller is **clean, readable, and production-aligned**.
