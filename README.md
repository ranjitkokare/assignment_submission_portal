# Assignment Submission Portal

## Description
This project is a Spring Boot-based backend system for an **Assignment Submission Portal**. It supports:
- MongoDB as the database (MongoDB Compass for GUI).
- Email-based OTP for authentication.
- JWT for secure token management.
- Testing using Postman.

---

## Features
1. **User Registration and OTP-based Authentication**:
   - Sends OTP to registered email.
   - Validates OTP before completing registration.
   - Also, option to enable two-factor-authentication.
   
2. **JWT Token Management**:
   - Provides secure user sessions.
   
3. **MongoDB**:
   - Stores user details and submission data.
   
4. **Spring Boot Mail**:
   - Sends OTP via email.
   
---

## Prerequisites

### Software Requirements
1. **Java**: Ensure Java 17+ is installed.
2. **Spring Boot**: Uses version 3.3.5.
3. **MongoDB**: Install MongoDB and MongoDB Compass for database operations.
4. **Postman**: For API testing (Postman Collection URL: [Documentation](https://documenter.getpostman.com/view/33540913/2sAYBPnEoB)).

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd assignment_submission_portal
```

### 2. Configure Environment Variables
#### Email Configuration
Update the `application.yml` file with your email and password:
```yaml
spring:
  mail:
    username: youremail@example.com
    password: your_password
```
> **Note**: Ensure to enable **"Allow less secure apps"** or generate an **App Password** for Gmail.

### 3. MongoDB Setup
1. Install MongoDB on your system and ensure it is running.
2. Default configuration:
   - **Database**: `assignment_portal_db`
   - **Host**: `localhost`
   - **Port**: `27017`

Update these in `application.properties` if needed:
```properties
spring.data.mongodb.database=assignment_portal_db
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
```

### 4. Build the Application
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```
The application will start at [http://localhost:8080/api/v1](http://localhost:8080/api/v1).

---

## API Documentation
Detailed API documentation and test cases are provided via Postman:
[Postman Collection URL](https://documenter.getpostman.com/view/33540913/2sAYBPnEoB)

---

## Dependencies
Here are the core dependencies from `pom.xml`:
- **Spring Boot Starter Data MongoDB**
- **Spring Boot Starter Web**
- **Spring Boot Starter Security**
- **Spring Boot Starter Validation**
- **Spring Boot Starter Mail**
- **JWT for Token Management**
- **Lombok** for boilerplate code reduction.

---

## Testing the Application

### 1. Using Postman
- Import the [Postman Collection](https://documenter.getpostman.com/view/33540913/2sAYBPnEoB).
- Test the APIs for Registration, Login, OTP validation, and JWT-based access.

### 2. Logging
Logs are configured in `application.properties`:
```properties
logging.level.org.springframework.web=DEBUG
logging.level.com.assignmentportal=DEBUG
```

---

## Additional Notes

### Security Configuration
- **JWT** is used for session management.
- Passwords and OTPs are stored using **BCrypt hashing**.

### Database Schema
- **Users Collection**: Stores user details.
- **OTP Collection**: Tracks OTPs for authentication.
- **Assignments Collection**: Stores assignments details.

### Email Setup
- Configure `application.yml` for SMTP settings.
- Use Gmail or a similar service for sending emails.

---

## Troubleshooting

### Common Issues
1. **MongoDB Connection Issue**:
   - Ensure MongoDB is running on the configured host and port.

2. **Email Not Sent**:
   - Check your email configuration in `application.yml`.
   - Verify your Gmail settings for third-party app access or App Password.

3. **Postman API Errors**:
   - Verify the API base URL and endpoint paths.
---
