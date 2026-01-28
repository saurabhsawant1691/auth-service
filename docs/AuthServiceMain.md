# AuthServiceMain.java – Application Entry Point

## File Location

```
src/main/java/org/dnyanyog/AuthServiceMain.java
```

---

## Purpose of this File

`AuthServiceMain` is the **starting point of the Spring Boot application**. This is the class that:

* Boots the Spring container
* Triggers auto-configuration
* Starts the embedded web server (Tomcat)

Without this file, the application **cannot run**.

---

## Code Overview

```java
@SpringBootApplication
public class AuthServiceMain {

    public static void main(String args[]) {
        SpringApplication.run(AuthServiceMain.class, args);
    }
}
```

---

## Key Annotations Explained

### `@SpringBootApplication`

This is a **meta-annotation** that combines three critical annotations:

1. `@Configuration`

   * Marks this class as a source of Spring bean definitions

2. `@EnableAutoConfiguration`

   * Tells Spring Boot to automatically configure beans based on:

     * Classpath dependencies
     * application.properties
     * Environment

3. `@ComponentScan`

   * Scans the **current package (`org.dnyanyog`) and all sub-packages**
   * Detects:

     * `@Component`
     * `@Service`
     * `@Repository`
     * `@Controller`

📌 **Important Rule:**

> This class should always be placed at the **root package** of the project.

---

## The `main` Method

```java
SpringApplication.run(AuthServiceMain.class, args);
```

### What happens internally

1. Spring Boot starts
2. ApplicationContext is created
3. Auto-configuration kicks in
4. Beans are instantiated
5. Embedded Tomcat starts
6. Application begins listening on configured port

This single line replaces:

* web.xml
* ApplicationContext XML
* Manual server startup

---

## Role in Application Lifecycle

```text
JVM starts
   ↓
main() method invoked
   ↓
Spring context initialized
   ↓
Security filters loaded
   ↓
Controllers registered
   ↓
Application ready to accept requests
```

---

## Why No Other Code Exists Here

Best practice dictates:

* **No business logic** in this class
* **No configuration logic** in this class
* Only responsibility: **bootstrapping**

Keeping this class minimal ensures:

* Clean architecture
* Easy debugging
* Predictable startup behavior

---

## Common Mistakes to Avoid

❌ Placing this class inside a deep package (breaks component scanning)

❌ Adding configuration logic here instead of `config` package

❌ Renaming class without updating build/run configs

---

## Revision Checklist

* [ ] Is this class at root package level?
* [ ] Does it contain only the `main` method?
* [ ] Is `@SpringBootApplication` present?
* [ ] Does the app start with `java -jar`?

---

## Interview-Level Summary

> `AuthServiceMain` is the entry point that initializes the Spring Boot runtime, enables auto-configuration, scans components, and launches the embedded server.

---