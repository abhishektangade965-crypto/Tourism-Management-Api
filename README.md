
# 🌍 Tourism Hub

> **Tourism Hub** is a responsive travel and tourism web application developed using HTML, CSS, and JavaScript. Although this project is frontend-focused, it was built following software engineering principles and an API-ready architecture, making it suitable for future integration with Java Spring Boot REST APIs. The project demonstrates clean code organization, modular design, responsive development, and deployment skills expected from aspiring Java Backend Developers.

---

## 📸 Preview

<!-- Add screenshots here -->

<p align="center">
  <img src="https://github.com/user-attachments/assets/e154d5bc-b4e0-4d5c-b18c-71022f756283" width="90%">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/fdbc6cb4-d698-41e8-9878-c911b62d08e4" width="48%">
  <img src="https://github.com/user-attachments/assets/ab8d02f5-fc86-4b67-ad84-cd3f594870f5" width="48%">
</p>

---

# 🚀 Live Demo

🌐 https://tourism-hub-w4q7.onrender.com/

---

🎉 Tourism Management REST API - Enterprise Edition v2.0
An enterprise-grade, production-ready Spring Boot REST API for managing tourist information, bookings, and tour packages. Features JWT authentication, role-based authorization, OpenAPI 3 (Swagger) integration, request pagination, rolling file logging, and Docker containerization.

🚀 Key Enterprise Enhancements (v2.0)
Spring Security + JWT:
User authentication and registration
JWT tokens with 24-hour expiry
Role-Based Access Control (ADMIN, USER)
Secure password hashing with BCrypt
Swagger/OpenAPI UI:
Auto-generated API documentation and interactive testing interface
Accessible at http://localhost:8080/swagger-ui.html
Configured with Bearer token authentication support
Paging & Sorting:
Request paginated listings (Page<Tourist>) to reduce database overhead
Endpoints support page, size, sortBy, and direction query params
Professional Logging:
Integrated SLF4J and Logback logging
Configuration rolls file logs daily or when size exceeds 10MB, retaining 30 days of archives
Dockerization:
Multi-stage build Dockerfile (compilation in Java 21 JDK -> packaging in light JRE)
Docker Compose script to launch database and API containers instantly
📁 Project Directory Structure
tourism-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── TourismManagementApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java         (Spring Security Configuration)
│   │   │   │   └── SwaggerConfig.java          (Swagger OpenAPI Configuration)
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java         (NEW - User Register & Login)
│   │   │   │   └── TouristController.java      (Updated - Pagination & Logging)
│   │   │   ├── service/
│   │   │   │   ├── TouristService.java
│   │   │   │   └── impl/
│   │   │   │       └── TouristServiceImpl.java
│   │   │   ├── repository/
│   │   │   │   ├── TouristRepository.java
│   │   │   │   └── UserRepository.java          (NEW - User Security Store)
│   │   │   ├── entity/
│   │   │   │   ├── Tourist.java
│   │   │   │   ├── TourPackage.java
│   │   │   │   ├── User.java                   (NEW - User Entity)
│   │   │   │   └── Role.java                   (NEW - Role Enum)
│   │   │   ├── dto/
│   │   │   │   ├── TouristDTO.java
│   │   │   │   ├── TourPackageDTO.java
│   │   │   │   ├── LoginRequest.java           (NEW - Auth DTOs)
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   └── AuthResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── response/
│   │   │   │   └── ApiResponse.java
│   │   │   └── util/
│   │   │       └── TouristMapper.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback-spring.xml              (NEW - Logging Policies)
│   └── test/
│       └── java/com/example/
│           ├── service/
│           │   └── TouristServiceTest.java
│           └── controller/
│               └── TouristControllerIntegrationTest.java
├── pom.xml                                     (Spring Boot 3.2.0, Java 21)
├── Dockerfile                                  (Multi-stage build)
├── docker-compose.yml                          (MySQL + API Container Stack)
└── database-schema.sql                         (Schema + Seed Data)
🛠️ Technology Stack
Framework: Spring Boot 3.2.0
Security: Spring Security 6.2.0 (JWT + BCrypt)
Language: Java 21
Database: MySQL 8.0 / H2 (testing)
API Docs: Springdoc OpenAPI 2.3.0
Logging: SLF4J + Logback
Build Tool: Maven
⚡ Quick Start
For detailed settings, check QUICK_START_v2.md and SETUP_GUIDE.md.

Docker (Recommended)
docker-compose up -d
# Go to http://localhost:8080/swagger-ui.html
Local (Maven)
Initialize the database schema and sample data: mysql -u root -p < database-schema.sql
Run the application: mvn spring-boot:run
🔑 Default Login Credentials
Seeded automatically on startup:

Admin: admin / admin123 (Roles: ROLE_ADMIN, ROLE_USER)
User: user / user123 (Roles: ROLE_USER)
📖 Key API Endpoints
Authentication (Public)
POST /api/auth/register - Create user
POST /api/auth/login - Obtain JWT Token
GET /api/auth/me - Inspect active user details
Tourists (Requires Authentication)
GET /api/tourists - Paginated query (?page=0&size=10&sortBy=id&direction=ASC)
POST /api/tourists/register - Create tourist record
GET /api/tourists/{id} - Find tourist by ID
PUT /api/tourists/{id} - Update tourist
PUT /api/tourists/{id}/confirm - Confirm tourist booking
PUT /api/tourists/{id}/cancel - Cancel booking
GET /api/tourists/search/country?country=USA - Find by country
DELETE /api/tourists/{id} - Soft Delete / Deactivate (ADMIN only)
DELETE /api/tourists/{id}/permanent - Hard Delete (ADMIN only)
GET /api/tourists/count/active - Active count (ADMIN only)
🧪 Testing
Run standard JUnit 5 test suites using Maven:

# Run all tests (utilizes Mockito and MockMvc security testing context)
mvn test

# 🚀 Installation

Clone

```bash
git clone https://github.com/abhishektangade965-crypto/tourism-Hub.git
```

Navigate

```bash
cd tourism-Hub
```

Run

```text
Open index.html
```

or

```text
VS Code → Live Server
```

---

# 🎯 Skills Demonstrated

## Java Backend

- Core Java
- OOP Concepts
- Collections
- Exception Handling
- Multithreading
- JDBC
- REST API Understanding
- MVC Architecture
- Client-Server Architecture
- Git Version Control

## Frontend

- HTML5
- CSS3
- JavaScript
- Bootstrap
- Responsive Design
- DOM Manipulation

## Development Practices

- Clean Code
- Modular Architecture
- Git & GitHub
- Deployment
- Debugging
- Performance Optimization

---

# 🚀 Future Enhancements

- Spring Boot Backend
- REST APIs
- MySQL Database
- Hibernate ORM
- Spring Data JPA
- Spring Security
- JWT Authentication
- User Registration/Login
- Booking System
- Payment Integration
- Google Maps API
- Email Service
- Admin Dashboard
- Docker
- CI/CD
- AWS Deployment
- Swagger API Documentation
- JUnit Testing

---

# 🤝 Contributing

```bash
git checkout -b feature/new-feature
git commit -m "Add new feature"
git push origin feature/new-feature
```

Create Pull Request.

---

# 👨‍💻 Developer

**Abhishek Tangade**

GitHub:
https://github.com/abhishektangade965-crypto

---

# 💼 Why This Project Matters for Java Backend Interviews

This project showcases:

- Clean project organization
- Software engineering principles
- Responsive frontend development
- API-ready architecture
- Git workflow
- Deployment experience
- Full-stack development mindset
- Preparation for Spring Boot backend integration

> While this project currently focuses on the frontend, it is intentionally structured to evolve into a full-stack Java Spring Boot application, demonstrating scalability and backend readiness.

---

# 📄 License

MIT License

---

⭐ If you found this project useful, consider giving it a Star.
````
