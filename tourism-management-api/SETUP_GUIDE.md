# Tourism Management API - Complete Setup Guide

## 📋 Project Overview

This is a complete Spring Boot REST API application for Tourism Management with:
- Full CRUD operations for tourist management
- Booking management system
- Tour package filtering
- Country-wise filtering
- Input validation and exception handling
- Unit and integration tests
- MySQL database integration
- RESTful API design patterns

## 🚀 Step-by-Step Setup Instructions

### Step 1: Prerequisites Installation

#### Install Java 11 or Higher
```bash
# For Windows
Download from https://www.oracle.com/java/technologies/downloads/

# For Mac
brew install java11

# For Linux
sudo apt-get install openjdk-11-jdk

# Verify
java -version
```

#### Install MySQL
```bash
# For Windows
Download from https://dev.mysql.com/downloads/mysql/

# For Mac
brew install mysql

# For Linux
sudo apt-get install mysql-server

# Start MySQL Service
# Windows: Runs automatically
# Mac: brew services start mysql
# Linux: sudo service mysql start
```

#### Install Maven
```bash
# For Windows
Download from https://maven.apache.org/

# For Mac
brew install maven

# For Linux
sudo apt-get install maven

# Verify
mvn --version
```

### Step 2: Database Setup

1. **Open MySQL Command Line**
   ```bash
   mysql -u root -p
   ```

2. **Execute Database Script**
   ```sql
   -- Copy entire database-schema.sql content
   -- Or run commands one by one
   ```

3. **Verify Setup**
   ```sql
   USE tourism_management_db;
   SELECT * FROM tourists;
   ```

### Step 3: Configure Application

1. **Update Database Credentials**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tourism_management_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

2. **Optional Configuration**
   ```properties
   server.port=8080
   logging.level.com.example=DEBUG
   ```

### Step 4: Build Project

```bash
# Navigate to project directory
cd tourism-management-api

# Clean and build
mvn clean install

# Skip tests during build (optional)
mvn clean install -DskipTests
```

### Step 5: Run Application

#### Option 1: Using Maven
```bash
mvn spring-boot:run
```

#### Option 2: Using Java
```bash
java -jar target/tourism-management-api-1.0.0.jar
```

#### Option 3: Using IDE
- Right-click on TourismManagementApplication.java
- Select Run or Run As → Java Application

### Step 6: Verify Running Application

```bash
# Test the API
curl http://localhost:8080/api/tourists

# Expected Response:
{
  "success": true,
  "message": "All tourists retrieved successfully",
  "data": [...],
  "timestamp": "2024-01-10T10:30:45"
}
```

## 🧪 Running Tests

### Unit Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TouristServiceTest

# Run with output
mvn test -X
```

### Integration Tests
```bash
mvn test -Dtest=TouristControllerIntegrationTest
```

### Generate Report
```bash
mvn test surefire-report:report
```

## 📝 API Usage Examples

### 1. Register Tourist
```bash
curl -X POST http://localhost:8080/api/tourists/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "9876543210",
    "country": "USA",
    "passportNumber": "US12345678",
    "tourPackage": "Gold Package",
    "checkInDate": "2024-01-15",
    "checkOutDate": "2024-01-20",
    "roomNumber": "101",
    "totalCost": 5000.00
  }'
```

### 2. Get All Tourists
```bash
curl http://localhost:8080/api/tourists
```

### 3. Get Tourist by ID
```bash
curl http://localhost:8080/api/tourists/1
```

### 4. Get Tourists by Country
```bash
curl http://localhost:8080/api/tourists/country/USA
```

### 5. Get Tourists by Tour Package
```bash
curl http://localhost:8080/api/tourists/package/Gold\ Package
```

### 6. Confirm Booking
```bash
curl -X PUT http://localhost:8080/api/tourists/1/confirm
```

### 7. Update Tourist
```bash
curl -X PUT http://localhost:8080/api/tourists/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@example.com",
    "phone": "9876543210",
    "country": "Canada",
    "passportNumber": "CA12345678",
    "tourPackage": "Platinum Package",
    "checkInDate": "2024-01-15",
    "checkOutDate": "2024-01-25",
    "roomNumber": "105",
    "totalCost": 6500.00
  }'
```

### 8. Cancel Booking
```bash
curl -X PUT http://localhost:8080/api/tourists/1/cancel
```

### 9. Delete Tourist
```bash
curl -X DELETE http://localhost:8080/api/tourists/1
```

## 📊 Project Structure

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── TourismManagementApplication.java (Main class)
│   │   ├── controller/
│   │   │   └── TouristController.java (REST endpoints)
│   │   ├── service/
│   │   │   ├── TouristService.java (Interface)
│   │   │   └── impl/
│   │   │       └── TouristServiceImpl.java (Implementation)
│   │   ├── repository/
│   │   │   └── TouristRepository.java (JPA repository)
│   │   ├── entity/
│   │   │   └── Tourist.java (Database entity)
│   │   ├── dto/
│   │   │   └── TouristDTO.java (Data transfer object)
│   │   ├── exception/
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── response/
│   │   │   └── ApiResponse.java
│   │   ├── config/
│   │   │   └── AppConfig.java
│   │   └── util/
│   │       └── TouristMapper.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/
        ├── service/
        │   └── TouristServiceTest.java
        └── controller/
            └── TouristControllerIntegrationTest.java
```

## 🛠️ Troubleshooting

### Issue: MySQL Connection Failed
```
Solution:
1. Verify MySQL service is running
2. Check credentials in application.properties
3. Ensure database exists
4. Check MySQL port (default: 3306)
```

### Issue: Port 8080 Already in Use
```
Solution 1: Change port in application.properties
server.port=8081

Solution 2: Kill process using port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8080
kill -9 <PID>
```

### Issue: Maven Build Fails
```
Solution:
1. mvn clean install
2. Delete .m2/repository folder
3. mvn install again
```

### Issue: Tests Fail
```
Solution:
1. Ensure MySQL is running
2. Check if database is created
3. Run: mvn test -DskipTests=false
```

## 📚 Key Classes

### Entity
- **Tourist.java** - JPA entity for tourist data

### DTOs
- **TouristDTO.java** - Data transfer object with validation

### Repositories
- **TouristRepository.java** - JPA repository with custom queries

### Services
- **TouristService.java** - Business logic interface
- **TouristServiceImpl.java** - Implementation

### Controllers
- **TouristController.java** - REST API endpoints (19 endpoints)

### Exception Handling
- **ResourceNotFoundException.java** - Custom exception
- **GlobalExceptionHandler.java** - Centralized exception handling

### Utilities
- **TouristMapper.java** - DTO-Entity conversion
- **ApiResponse.java** - Response wrapper

### Configuration
- **AppConfig.java** - CORS and Spring configuration

## 🔐 Security Considerations

1. **Input Validation** - All inputs validated before processing
2. **Exception Handling** - Errors don't expose sensitive info
3. **CORS** - Configured for allowed origins
4. **SQL Injection** - Protected via JPA parameterized queries
5. **Logging** - Sensitive data not logged

## 📦 Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 2.7.0 | Framework |
| Spring Data JPA | 2.7.0 | ORM |
| MySQL Connector | 8.0.33 | Database |
| Lombok | Latest | Annotations |
| JUnit | 4 | Testing |
| Mockito | Latest | Mocking |

## 🎯 Next Steps

1. ✅ Setup database
2. ✅ Configure credentials
3. ✅ Build project
4. ✅ Run application
5. → Test APIs using Postman
6. → Customize as needed
7. → Deploy to production

## 💡 Tips & Tricks

- Use **Postman Collection** for API testing
- Check **logs** directory for debugging
- Modify **EmployeeDTO.java** for validation rules
- Extend functionality in **TouristController.java**
- Review test examples in **test** package

## 📞 Support

For issues:
1. Check troubleshooting section
2. Review log files
3. Check Spring Boot documentation
4. Review README.md

---

**Happy Tourism API Development! 🌍✈️**
