# Employee Management System

A secure RESTful Employee Management System built using **Spring Boot** that demonstrates modern backend development practices including **JWT Authentication**, **Role-Based Authorization**, **Spring Security**, **Swagger**, **Validation**, **Global Exception Handling**, **Spring AOP**, **Interceptor**, **Pagination**, and **MySQL**.

This project follows a layered architecture (Controller → Service → Repository) and is designed as a backend-only application for managing employees securely.

---

# Features

## Employee Management

- Create Employee
- Get All Employees (Pagination)
- Get Employee by ID
- Get Employee by Email
- Get Employees by Department
- Search Employees by Name
- Update Employee
- Soft Delete Employee
- Permanent Delete Employee

---

## Authentication & Authorization

- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Role-Based Authorization (ADMIN / USER)
- Stateless Authentication

---

## Backend Features

- Spring Security
- JWT Authentication Filter
- Spring Interceptor
- Spring AOP
- Global Exception Handling
- Request Validation
- Logging
- Pagination
- Search APIs
- Soft Delete
- JPA Auditing
- CORS Configuration
- Swagger / OpenAPI Documentation
- API Versioning

---

# Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Database

- MySQL

## Authentication

- JWT (JSON Web Token)
- BCrypt Password Encoder

## Documentation

- Swagger OpenAPI

---

# Project Structure

```
src/main/java
│
├── aspect
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── filter
├── interceptor
├── repository
├── response
└── service
```

---

# Architecture

```
                 Client
                    │
                    ▼
          Spring Security
                    │
                    ▼
      JWT Authentication Filter
                    │
                    ▼
             Spring Interceptor
                    │
                    ▼
               Controllers
                    │
                    ▼
                 Services
                    │
                    ▼
               Repositories
                    │
                    ▼
                  MySQL
```

---

# Authentication Flow

```
User Registration
        │
        ▼
Password Encrypted using BCrypt
        │
        ▼
Stored in MySQL
        │
        ▼
User Login
        │
        ▼
JWT Token Generated
        │
        ▼
Authorization: Bearer <JWT>
        │
        ▼
JWT Authentication Filter
        │
        ▼
Role Validation
        │
        ▼
Protected APIs
```

---

# API Endpoints

## Authentication

| Method | Endpoint | Access |
|---------|----------|--------|
| POST | /api/v1/users | Public |
| POST | /api/v1/auth/login | Public |

---

## Employee APIs

| Method | Endpoint | Access |
|---------|----------|--------|
| POST | /api/v1/employees | ADMIN |
| GET | /api/v1/employees | ADMIN / USER |
| GET | /api/v1/employees/{id} | ADMIN / USER |
| GET | /api/v1/employees/email/{email} | ADMIN / USER |
| GET | /api/v1/employees/name/{name} | ADMIN / USER |
| GET | /api/v1/employees/department/{department} | ADMIN / USER |
| PUT | /api/v1/employees/{id} | ADMIN |
| DELETE | /api/v1/employees/soft/{id} | ADMIN |
| DELETE | /api/v1/employees/{id} | ADMIN |

---

# Security

- JWT Authentication
- Role-Based Authorization
- BCrypt Password Hashing
- Stateless Session Management
- JWT Authentication Filter
- CORS Enabled

---

# Validation

This project uses **Jakarta Bean Validation**.

Examples include:

- Email Validation
- Required Field Validation
- Invalid Credentials Handling
- Validation Error Responses

---

# Exception Handling

Centralized exception handling using **@RestControllerAdvice**.

Custom exceptions include:

- Resource Not Found
- Duplicate Resource
- Invalid Credentials
- Validation Errors
- Generic Exceptions

---

# Swagger

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

### Steps

1. Register a new user.
2. Login using `/api/v1/auth/login`.
3. Copy the generated JWT.
4. Click **Authorize**.
5. Enter:

```
Bearer <your_jwt_token>
```

6. Access all protected APIs.

---

# Database

Database used:

- MySQL

Example configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
```

---

# Screenshots

## Swagger UI

![Swagger](screenshots/swagger-home.png)

---

## JWT Authorization

![JWT Authorization](screenshots/swagger-authorize.png)

---

## Login

![Login](screenshots/login-success.png)

---

## Employee APIs

![Employees](screenshots/get-employees.png)

---

## Validation

![Validation](screenshots/validation-error.png)

---

## Unauthorized Request

![Unauthorized](screenshots/unauthorized.png)

---

## Database

### Employee Table

![Employee Table](screenshots/employee-table.png)

### User Table

![User Table](screenshots/user-table.png)

---

## Project Structure

![Project Structure](screenshots/project-structure.png)

---

# Running the Project

## Clone Repository

```bash
git clone https://github.com/<your-github-username>/employee-management-system.git
```

---

## Create Database

```sql
CREATE DATABASE employee_db;
```

---

## Configure

```properties
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

---

## Build

```bash
mvn clean install
```

---

## Run

```bash
mvn spring-boot:run
```

---

# Future Improvements

- Unit Testing (JUnit & Mockito)
- Docker Support
- CI/CD Pipeline
- Email Notifications
- File Upload Support
- Cloud Deployment

---

# Author

**Yogesh Pal**

Java Backend Developer