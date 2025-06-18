# FinanceControl
Application for personal financial management using transactions; with authentication functionalities and user management.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.1-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-informational)
![License](https://img.shields.io/badge/license-MIT-brightgreen)
![Build](https://img.shields.io/badge/build-passing-success)
![Docker](https://img.shields.io/badge/docker-ready-blue)
![Tests](https://img.shields.io/badge/tests-coverage-yellow)

 ---

- [Tech Stack](#tech-stack)
- [App in use](#app-in-use)
- [Features](#features)
- [Continuous Integration (CI)](#continuous-integration-ci)
- [How to Run the Project](#how-to-run-the-project)
- [Docker](#docker)
- [Diagrams and Architecture](#diagrams-and-architecture)
- [Backend Update Checklist](#backend-update-checklist)
- [Frontend Update Checklist](#frontend-update-checklist)

## Tech Stack

- **Java 21 + Spring Boot**
- **JWT** for authentication
- **Hibernate + JPA** for database connection
- **MySQL** database
- **Lombok** for eliminating boilerplate code
- **Swagger** for API docs
- **Postman** for manual testing
- **JUnit** & **Mockito** for automated backend testing
- **Docker** for containerization
  
---

## App in use
![image](https://github.com/user-attachments/assets/ac4d2e92-98ec-452b-b1f3-320708a0f852)
![image](https://github.com/user-attachments/assets/53d66d49-01dc-470d-b4e4-e8bd078891a4)
![image](https://github.com/user-attachments/assets/7ae7fe18-1f41-4f48-8ee6-af19123f34bd)
![image](https://github.com/user-attachments/assets/3d77e51c-883e-4b93-866a-ee32798efa34)


 ---

## Features

- User authentication with JWT
- Transaction management (add, edit, delete)
- Role-based access control
- Swagger API documentation
- Docker Compose setup for easy deployment

 ---
## Continuous Integration (CI)
This project uses **GitHub Actions** to automate testing and builds for both the backend and frontend on every `push` or `pull_request` to any branch.

### How It Works

#### Backend Job
- Starts a MySQL 8.0 container for integration tests.
- Waits until the database is available before proceeding.
- Sets up Java 21 and runs `mvn clean test`.
- Builds the backend Docker image (`financecontrol-backend`).

#### Frontend Job
- Sets up Node.js 20.
- Installs dependencies using `npm ci`.
- 
You can find the workflow file at .github/workflows/ci.yml
![image](https://github.com/user-attachments/assets/05d5d5ca-cc71-4ac2-b659-b4fa8e2a10e5)

 ---
## How to Run the Project
- Running with Docker (recommended for production and isolated development)
- This project uses Docker Compose to orchestrate 3 containers:

|Service|Role|
|-         |-                                |
| db       | MySQL database container        |
| backend	 | Spring Boot backend application |
| frontend | Frontend application container  |

## Steps to run
1. Make sure Docker and Docker Compose are installed.

2. Copy .env.example to .env and adjust variables as needed.

3. Run this command in the project root folder:

`docker-compose up --build`

4. Services will be available at:
- Backend: http://localhost:8080
- Frontend: http://localhost (accessible via default HTTP port 80)
- MySQL: accessible internally by the backend container (default port 3306)
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- 
 ---
## Docker
### Container Architecture
- db: MySQL 8.0 container with persistent storage via Docker volume db_data.
- backend: Spring Boot application container, depends on the database.
- frontend: Frontend application container, depends on backend.

### Networking
All containers connect to a Docker network finance-net for secure, isolated communication.

### Communication flow
- Backend connects to DB via jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE} (defaults to jdbc:mysql://db:3306/financedb).
- Frontend calls backend APIs at http://backend:8080 within Docker network.
- Host machine exposes ports 3306, 8080, and 80 for external access.

### Useful commands

Start all containers:
```bash
docker-compose up --build
```

Stop all containers:
```bash
docker-compose down
```

View backend logs:
```bash
docker logs -f finance-backend
```

Access backend container shell:
```bash
docker exec -it finance-backend /bin/sh
```

 ---
## Diagrams and architecture

See folder [docs/](./docs) for more information about the project structure.
- [Use Case Diagram](./docs/UseCaseDiagram.jpg)
 ---

## Backend Update Checklist
### 🚧 Needs
- [ ] Export transactions to CSV
- [ ] Export transactions to PDF
- [ ] Add filtering by date range
- [ ] Add filtering by category
- [ ] Add filtering by type (INCOME/EXPENSE)

#### 🧪 Testing
- [ ] 70%
- [ ] 80%+
- [ ] Integration test for signin
- [ ] Integration test for adding transaction
- [ ] Integration test for getting transactions

### ✅ Done
#### 🧪 Testing
- [x] 20%
- [x] 50%

#### 🔐 Authentication and Authorization
- [x] Refresh tokens
- [x] Token Invalidation
- [x] JWT
- [x] JWT implementation
- [x] PasswordEncoder
- [x] Permissions
- [x] Roles
- [x] CORS config

#### 📦 Exceptions and Enums
- [x] GlobalExceptionHandler (@ControllerAdvice)
- [x] @Valid
- [x] Custom error messages
- [x] Exceptions

#### 👤 Entity Management
- [x] Models
- [x] DTOs
- [x] Enums
- [x] Repositories
- [x] UserController
- [x] UserService
- [x] UserUpdate Service
- [x] TransactionController
- [x] TransactionService
- [x] Refactor UserUpdateService to use both request and response DTOs
- [x] Change transactionSetDate to selectable day instead of .now
      
#### 🛠 Others
- [x] Postman
- [x] Swagger
- [x] Docker
- [x] JUNIT for testing
 ---

## Frontend Update Checklist
### 🚧 Needs

#### 💸 Transaction Management
- [ ] Edit existing transactions
- [ ] Filtering and sorting (by date, type, amount, etc.)
- [ ] Modal confirmation for transaction deletion

#### 📆 Budget & Planning
- [ ] Visual warning when budget is exceeded
- [ ] Monthly goal input screen

#### 👥 User Management (Admin)
- [ ] User listing screen
- [ ] Create new user (admin only)
- [ ] Edit user data
- [ ] Delete users
- [ ] Role and permission control (admin/user)

#### 🧑‍💼 User Experience Improvements
- [ ] Modal confirmation for transaction deletion
- [ ] Real-time updated user updates
- [ ] "Back to top" button
- [ ] Visual feedback after adding/editing transactions

#### 🖥️ General UI/UX
- [ ] Responsive design (mobile-first)
- [ ] Loading indicators
- [ ] Light/Dark theme support (optional)

### ✅ Done
#### 🔐 Authentication (JWT)
- [x] Login screen
- [x] Integration with backend for authentication
- [x] Secure storage of JWT token (e.g., localStorage or sessionStorage)
- [x] Route protection based on authentication
- [x] Logout (token removal and redirection)

#### 💸 Transaction Management
- [x] Add new transaction (income/expense)
- [x] Delete transactions
- [x] Transaction list for the logged-in user
- [x] Balance persistance

#### 🧑 Profile Update (User Self-Service)
- [x] User profile screen
- [x] Edit personal information (name, email, password, etc.)
- [x] Form validation
- [x] Visual feedback on success/error

#### 📆 Budget & Planning
- [x] Display remaining budget for each category
- [x] Budget by category input

#### 🖥️ General UI/UX
- [x] Clear error/success messages
- [x] Reusable components

