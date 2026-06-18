# 📦 TOURISM MANAGEMENT API v2.0 - ENTERPRISE FEATURES

This document describes the design and implementation of enterprise features added in the **v2.0 Enterprise Edition** of the Tourism Management System.

---

## 1. Spring Security + JWT Authentication & Authorization

The application uses **Spring Security 6.2** to implement stateless token-based authentication.

### Workflow:
1. **Login**: The client sends a `POST` request to `/api/auth/login` containing `usernameOrEmail` and `password`.
2. **Authentication**: `AuthController` uses `AuthenticationManager` with `CustomUserDetailsService` to verify credentials against the database. If verification succeeds, a JWT token is generated.
3. **Token Structure**:
   - Signature Algorithm: `HS256`
   - Subject: Username
   - Custom Claims: `roles` (list of authorities)
   - Expiration: 24 hours (86,400,000 milliseconds)
4. **Subsequent requests**: Clients must attach the JWT token to the `Authorization` header as a Bearer token:
   `Authorization: Bearer <token>`
5. **Request Filtering**: `JwtAuthenticationFilter` intercepts all incoming requests, extracts the JWT, validates it, fetches the associated user details, and sets the Spring Security context.

### Access Control Policies (RBAC):
- **Public Endpoints**:
  - `/api/auth/**` (Registration, Login, User Details)
  - `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**` (API Documentation)
- **ADMIN Role Only**:
  - `DELETE /api/tourists/{id}` (Soft delete/deactivation)
  - `DELETE /api/tourists/{id}/permanent` (Hard delete)
  - `/api/tourists/count/**` (Statistics and reports)
- **USER or ADMIN Roles**:
  - All other `/api/tourists/**` endpoints (Read, create, update, search, confirm bookings)

---

## 2. Interactive API Documentation (Swagger / OpenAPI 3)

We integrated **Springdoc OpenAPI Starter WebMVC UI 2.3.0** to auto-generate documentation and host the interactive Swagger UI interface.

- **Swagger UI Path**: `http://localhost:8080/swagger-ui.html` (redirects to `/swagger-ui/index.html`)
- **JSON API Specs Path**: `http://localhost:8080/v3/api-docs`
- **Security Integration**: Swagger UI is configured with a Bearer authentication scheme. To test protected endpoints:
  1. Authenticate via `/api/auth/login` to retrieve a JWT token.
  2. Click the **Authorize** button in Swagger UI.
  3. Enter the JWT token (without prefix) and save. All requests sent from the UI will now include the authorization header.

---

## 3. High Performance Pagination

In v1.0, retrieving tourists returned a complete list, which poses a memory and database load risk at scale. In v2.0, we introduced paging on the query.

- **Paging endpoint**: `GET /api/tourists`
- **Query Parameters**:
  - `page` (default: `0`): The page number (zero-based).
  - `size` (default: `10`): Number of records per page.
  - `sortBy` (default: `id`): Sort column.
  - `direction` (default: `ASC`): Sort direction (`ASC` or `DESC`).
- **Response Shape**: Returns a standard Spring Data `Page<T>` structure containing metadata (`totalElements`, `totalPages`, `size`, `number`, etc.) and the list of records in `content`.

---

## 4. Professional Rolling File Logging

Logging uses **SLF4J** interface with **Logback** implementation.

- **Configuration File**: `src/main/resources/logback-spring.xml`
- **Output Targets**:
  - **Console**: Structured output for terminal debugging.
  - **File**: Output saved to `logs/application.log`.
- **Log Rotation Policy**:
  - Rollover triggers: Daily at midnight OR when `application.log` reaches **10MB**.
  - History retention: Old logs are archived in `logs/archived/` with daily naming patterns, kept for **30 days**.
  - Capacity Cap: Total volume of archived logs is capped at **3GB** to protect system storage.

---

## 5. Dockerization & Container Orchestration

The application is configured to run inside secure container environments.

- **Dockerfile**: Implements a multi-stage Docker build:
  1. **Build stage**: Packages code using a lightweight Alpine Maven container (`maven:3.9.6-eclipse-temurin-21-alpine`) running Java 21, caching intermediate Maven packages.
  2. **Execution stage**: Runs the compiled jar using a minimal Temurin JRE container (`eclipse-temurin:21-jre-alpine`) to keep the image footprint small and secure.
- **docker-compose.yml**: Orchestrates the API and a MySQL database:
  - Database service (`db`) boots a MySQL 8 container, mounts a volume `mysql_data` for persistence, exposes port 3306, and runs health checks.
  - API service (`app`) builds the local project, overrides standard database settings to connect to the internal database container, and launches only after database health checks succeed.
  - Seeding: On initial database container build, the database is auto-seeded using `database-schema.sql`.
