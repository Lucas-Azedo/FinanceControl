# FinanceControl
Application for personal financial management using transactions; with authentication functionalities and user management.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.1-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-informational)
![License](https://img.shields.io/badge/license-MIT-brightgreen)
![Build](https://img.shields.io/badge/build-passing-success)

 ---

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


 ---
## Continuous Integration (CI)
This project uses GitHub Actions to automate testing and builds for both the backend and frontend every time code is pushed or a pull request is opened.

### How CI Works
Triggers: The CI workflow runs on every push and pull_request to any branch.

Backend Job:
- Spins up a MySQL 8.0 container for integration tests.
- Waits for the database to be ready before running tests.
- Builds and tests the backend using Maven with Java 21.
- Builds the backend Docker image (financecontrol-backend).

Frontend Job:
- Installs Node.js version 20.
- Installs frontend dependencies using npm ci.

![image](https://github.com/user-attachments/assets/05d5d5ca-cc71-4ac2-b659-b4fa8e2a10e5)

**You can find the workflow file at .github/workflows/ci.yml.**
 ---
## How to Run the Project
- Running with Docker (recommended for production and isolated development)
- This project uses Docker Compose to orchestrate 3 containers:


|Service|Role|
|-         |-                                 |
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
- Frontend:  Frontend: http://localhost (accessible via default HTTP port 80)
- MySQL: accessible internally by the backend container
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
docker exec -it finance-backend /bin/sh`
```

 ---
## Backend Update Checklist
### 🚧 Needs
- [ ] Data graphs
- [ ] @Slf4j
- [ ] Export (PDF)
- [ ] Endpoints for Graphs (transactions/summary , transactions/by-category , transactions/monthly);
- [ ] Pagination for Listing (?page=0&size=10&sort=date,desc.)
- [ ] Change transactionSetDate to selectable day instead of .now

#### 🧪 Testing
- [ ] 50%
- [ ] 70%
- [ ] 80%+

### ✅ Done
#### 🧪 Testing
- [x] 20%

#### 🔐 Authentication and Authorizatiom
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

#### 👥 User Management (Admin)
- [ ] User listing screen
- [ ] Create new user (admin only)
- [ ] Edit user data
- [ ] Delete users
- [ ] Role and permission control (admin/user)

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

#### 🖥️ General UI/UX
- [x] Clear error/success messages
- [x] Reusable components

