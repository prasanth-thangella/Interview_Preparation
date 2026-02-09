# Rest Assured Theory & Deep Dive

## 1. HTTP Fundametals
### Methods
- **GET**: Retrieve a resource. Idempotent (safe to reach multiple times).
- **POST**: Create a resource. Not idempotent.
- **PUT**: Update a resource completely (Reference replacement).
- **PATCH**: Update a resource partially.
- **DELETE**: Remove a resource.

### Status Codes
- **200 OK**: Success (GET)
- **201 Created**: Resource created (POST)
- **204 No Content**: Success but no body (DELETE)
- **400 Bad Request**: Client sent invalid data
- **401 Unauthorized**: No credentials or invalid credentials
- **403 Forbidden**: Valid credentials but no permission
- **404 Not Found**: Resource doesn't exist
- **500 Internal Server Error**: Bug in the server

## 2. Rest Assured Basics
Rest Assured uses a fluent interface inspired by BDD (Behavior Driven Development).

```java
given()
    .header("Content-Type", "application/json")
    .body(payload)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .body("name", equalTo("Morpheus"));
```

### Key Components
- **RequestSpecification**: How the request looks (URL, headers, body, auth).
- **Response**: What the server sent back (Status, headers, body).
- **ValidatableResponse**: Interface for `then()` validations.

## 3. Serialization & Deserialization (POJOs)
This is the most important concept for senior interviews. Avoid using Strings or HashMaps for complex payloads. Use **POJOs (Plain Old Java Objects)**.

### Serialization (Java -> JSON)
Converting a Java Object into a JSON payload to send in a request.
*Requires Jackson Databind dependency.*

```java
User user = new User("Prasanth", "QA Lead");
given().body(user).post("/users");
```

### Deserialization (JSON -> Java)
Converting a JSON response into a Java Object to validate logic.

```java
User responseUser = given().get("/users/1").as(User.class);
Assert.assertEquals(responseUser.getName(), "Prasanth");
```

## 4. Authentication Mechanism
### 1. Basic Auth
Base64 encoded username:password sent in header.
```java
given().auth().basic("user", "pass").get("/secure");
// Or manually
given().header("Authorization", "Basic <base64_string>").get("/secure");
```

### 2. Bearer Token (JWT)
Most common in modern connection.
```java
given().header("Authorization", "Bearer " + token).get("/profile");
```

### 3. OAuth 2.0
Involves getting an Access Token first using `client_id` and `client_secret`.
1. **POST** to `/token` endpoint with credentials -> Extract `access_token`.
2. Use token in subsequent requests.

## 5. Advanced Features
### RequestSpecBuilder
Reuse common configurations across multiple tests (Base URI, Common Headers).

```java
RequestSpecification spec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .addHeader("Content-Type", "application/json")
    .build();

given().spec(spec).get("/endpoint");
```

### Filters
Used for logging, modify requests globally, or custom reporting.
```java
given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter()).get("/");
```

### Relaxed HTTPS
Bypass SSL warnings in test environments.
```java
given().relaxedHTTPSValidation().get("/");
```
