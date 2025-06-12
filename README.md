# FinanceControl
Application for personal financial management using transactions; with authentication functionalities and user management.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.1-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-informational)
![License](https://img.shields.io/badge/license-MIT-brightgreen)
![Build](https://img.shields.io/badge/build-passing-success)

 ---

## 🔧 Tech Stack

- **Java 21 + Spring Boot**
- **JWT** for authentication
- **Hibernate + JPA** for database connection
- **MySQL** database
- **Lombok** for eliminating boilerplate code
- **Swagger** for API docs
- **Postman** for testing

 ---
 
## Backend Update Checklist
### 🚧 Needs
- [ ] JUNIT for testing
- [ ] Data graphs
- [ ] @Slf4j
- [ ] Docker
- [ ] Export (PDF)
- [ ] Endpoints for Graphs (transactions/summary , transactions/by-category , transactions/monthly);
- [ ] Pagination for Listing (?page=0&size=10&sort=date,desc.)
- [ ] PUT methods for all Services and Controllers
- [ ] Refactor UserUpdateService to use both request and response DTOs

### ✅ Done
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
      
#### 🛠 Others
- [x] Postman
- [x] Swagger

 ---

## Frontend Update Checklist
### Needs
#### 🔐 Authentication (JWT)

#### 💸 Transaction Management
- [ ] Transaction list for the logged-in user
- [ ] Edit existing transactions
- [ ] Delete transactions
- [ ] Filtering and sorting (by date, type, amount, etc.)

#### 👥 User Management (Admin)
- [ ] User listing screen
- [ ] Create new user (admin only)
- [ ] Edit user data
- [ ] Delete users
- [ ] Role and permission control (admin/user)

#### 🧑 Profile Update (User Self-Service)
- [ ] User profile screen
- [ ] Edit personal information (name, email, password, etc.)
- [ ] Form validation
- [ ] Visual feedback on success/error

#### 🖥️ General UI/UX
- [ ] Responsive design (mobile-first)
- [ ] Reusable components
- [ ] Loading indicators
- [ ] Clear error/success messages
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

