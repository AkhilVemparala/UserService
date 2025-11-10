### Overview
The **UserService** manages user accounts, authentication, and login sessions.  
It provides APIs for registration, login, and credential validation used by the BookingService and API Gateway.

---

## ⚙️ Tech Stack
| Component | Technology |
|------------|-------------|
| Framework | Spring Boot 3.x |
| Database | MySQL / JPA |
| Security | JWT Authentication (to be added) |
| Discovery | Eureka Client |
| Build Tool | Maven |
| Logging | Logback |

---

## 🏗️ Core Layers
- **Controller:** `UserserviceController` — handles user registration and login.
- **Service:** `LoginServiceImpl` — manages authentication, validation, and session creation.
- **Repository:** `UserDetailsRepository`, `LoginSessionRepository` — persists user and session data.
- **Entities:** `UserDetails`, `LoginSession`.

---

## 🔌 Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/users/register` | Register a new user |
| POST | `/users/login` | Authenticate user credentials |
| GET | `/users/{id}` | Fetch user details by ID |
| DELETE | `/users/logout/{id}` | Invalidate user session |

---

## ⚙️ Exception Handling
- `UserServiceException` — custom root exception.
- `UserNotFoundException`, `InvalidCredentialsException` — specialized errors.
- `GlobalExceptionHandler` — unified JSON responses.

---

## 📦 Future Improvements
- Add JWT-based authentication.
- Integrate Redis for session caching.
- Add password hashing and email verification.
