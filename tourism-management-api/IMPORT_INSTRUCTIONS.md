# Tourism Management API - Eclipse Import Instructions

## 📦 Import Project into Eclipse IDE

### Step 1: Extract ZIP File
- Extract the `tourism-management-api.zip` file to any location on your computer
- Example: `C:\projects\tourism-management-api`

### Step 2: Open Eclipse IDE

### Step 3: Import Project
Follow these steps to import the project:

**Method 1: Using File Menu (Recommended)**

1. Go to **File** → **Import**
2. Expand **Maven** folder
3. Select **Existing Maven Projects**
4. Click **Next**
5. Click **Browse**
6. Navigate to the extracted project folder (`tourism-management-api`)
7. Click **OK**
8. You should see `pom.xml` listed
9. Click **Finish**
10. Wait for Maven to download dependencies (first time may take 2-5 minutes)

**Method 2: Drag and Drop (Alternative)**

1. In Eclipse Package Explorer, right-click → **Import**
2. Select **Existing Projects into Workspace**
3. Click **Browse**
4. Select the extracted project folder
5. Ensure **Copy projects into workspace** is unchecked (optional)
6. Click **Finish**

### Step 4: Wait for Build

- Eclipse will start building the project
- You'll see progress bar at bottom right
- Once complete, you should see green checkmark on project

### Step 5: Configure Database

1. Open `src/main/resources/application.properties`
2. Update database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tourism_management_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

### Step 6: Create Database

1. Open MySQL Command Line or MySQL Workbench
2. Execute the SQL script: `database-schema.sql`
   ```bash
   mysql -u root -p < database-schema.sql
   ```

### Step 7: Run Application

**Method 1: Run as Spring Boot App**

1. Right-click on **TourismManagementApplication.java**
2. Select **Run As** → **Spring Boot App**
3. Application will start on `http://localhost:8080`

**Method 2: Run as Java Application**

1. Right-click on **TourismManagementApplication.java**
2. Select **Run As** → **Java Application**

**Method 3: Run with Maven**

1. Right-click on project
2. Select **Run As** → **Maven build**
3. Enter goals: `spring-boot:run`
4. Click **Run**

### Step 8: Verify Running Application

- Check Console tab for logs
- Look for message: "Tourism Management API Started Successfully!"
- Open browser and test: `http://localhost:8080/api/tourists`

## ✅ Common Issues & Solutions

### Issue: Maven Dependencies Not Downloaded
**Solution:**
1. Right-click on project
2. Select **Maven** → **Update Project** (or Ctrl+Alt+U)
3. Click **OK**
4. Wait for download to complete

### Issue: Java Version Error
**Solution:**
1. Right-click on project
2. Select **Properties**
3. Go to **Java Compiler**
4. Set Compiler Compliance Level to **11**
5. Apply and rebuild

### Issue: Cannot Find Spring Boot Main Class
**Solution:**
1. Ensure **TourismManagementApplication.java** is in correct package: `com.example`
2. Check that file exists in `src/main/java/com/example/`
3. Refresh project: F5

### Issue: MySQL Connection Error
**Solution:**
1. Verify MySQL is running
2. Check credentials in `application.properties`
3. Ensure database `tourism_management_db` exists
4. Check MySQL port (default: 3306)

### Issue: Port 8080 Already in Use
**Solution:**
1. Edit `application.properties`
2. Change: `server.port=8081` (or any free port)
3. Restart application

### Issue: Database Not Created
**Solution:**
1. Open MySQL Command Line
2. Create database: `CREATE DATABASE tourism_management_db;`
3. Use database: `USE tourism_management_db;`
4. Execute: `database-schema.sql`

## 🧪 Run Tests

### Run All Tests
1. Right-click on project
2. Select **Run As** → **JUnit Test**

### Run Specific Test Class
1. Open test class (e.g., `TouristServiceTest.java`)
2. Right-click on class name
3. Select **Run As** → **JUnit Test**

## 📝 Project Structure

```
tourism-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── TourismManagementApplication.java
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── response/
│   │   │   ├── config/
│   │   │   └── util/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/
├── pom.xml
├── .project
├── .classpath
├── .gitignore
├── README.md
├── SETUP_GUIDE.md
├── database-schema.sql
└── Postman_Collection.json
```

## 📖 API Documentation

Once running, access:
- **All Tourists**: `http://localhost:8080/api/tourists`
- **Tourist by ID**: `http://localhost:8080/api/tourists/1`
- **Postman Collection**: Import `Postman_Collection.json` into Postman

## 🎯 Next Steps

1. ✅ Extract ZIP file
2. ✅ Import into Eclipse
3. ✅ Configure database credentials
4. ✅ Create database
5. ✅ Run application
6. ✅ Test APIs using Postman
7. → Customize as needed
8. → Deploy to production

## 💡 Tips

- Use **Ctrl+Shift+R** to refresh project in Eclipse
- Use **Ctrl+Alt+U** to update Maven dependencies
- Check **Console** tab for application logs
- Use **Debug As** for debugging

## 📞 Troubleshooting

If you encounter any issues:
1. Check Eclipse Console for error messages
2. Verify MySQL is running
3. Ensure Java 11+ is installed
4. Update Maven dependencies
5. Clean and rebuild project: **Project** → **Clean**

## 🌍 Ready to Go!

Your Tourism Management API is now ready to use in Eclipse! 🚀

For more information, see:
- `README.md` - Complete API documentation
- `SETUP_GUIDE.md` - Detailed setup instructions
- `database-schema.sql` - Database structure
