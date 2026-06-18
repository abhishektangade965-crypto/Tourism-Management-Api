# 🚀 QUICK START - v2.0 ENTERPRISE EDITION

This guide helps you launch the Enterprise Edition of the Tourism Management System API in under 2 minutes.

---

## Option 1: Using Docker Compose (Easiest & Recommended)

Make sure you have **Docker** and **Docker Compose** installed.

```bash
# 1. Start the container stack (MySQL + Spring Boot API)
docker-compose up -d

# 2. Check the logs to ensure the application has started
docker-compose logs -f app

# 3. Access the interactive API docs:
# Swagger UI: http://localhost:8080/swagger-ui.html
```

---

## Option 2: Running Locally (Java + MySQL)

### Prerequisites:
- Java 21 JDK installed
- Maven 3.6+ installed
- MySQL Server 8.0 running locally

### Steps:

1. **Update Credentials**:
   Open `src/main/resources/application.properties` and update database login details if they differ from the defaults:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tourism_management_db
   spring.datasource.username=root
   spring.datasource.password=root
   ```

2. **Initialize Database**:
   Import the SQL schema and data seed script into your MySQL instance:
   ```bash
   mysql -u root -p < database-schema.sql
   ```

3. **Run the Application**:
   Run using Maven from the project directory:
   ```bash
   mvn spring-boot:run
   ```

4. **Verify Application**:
   - Access Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🔐 Default Login Credentials

Use these credentials to authenticate and obtain your JWT token:

### Administrator Access:
- **Username**: `admin`
- **Password**: `admin123`

### User Access:
- **Username**: `user`
- **Password**: `user123`

---

## 🛠️ Testing APIs with Swagger UI

1. Open `http://localhost:8080/swagger-ui.html`.
2. Expand the `Auth` tag and select `POST /api/auth/login`.
3. Click **Try it out** and enter the credentials (e.g., admin / admin123).
4. Execute the request. Copy the `accessToken` string from the JSON response.
5. Scroll to the top and click the **Authorize** button.
6. Enter the copied token in the input box and click **Authorize**.
7. Close the dialog. You can now execute and test all protected endpoints (e.g. registered under the `Tourists` tag).
