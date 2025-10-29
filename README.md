# Pepper Boss — RESTful Web API (Java • Spring Boot • MySQL)

[![Java](https://img.shields.io/badge/Java-17%2B-blue)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)]()
[![MySQL](https://img.shields.io/badge/Database-MySQL-orange)]()
[![Build](https://img.shields.io/badge/Build-Maven-lightgrey)]()

A backend API for managing **peppers**, **sauces**, and **ingredients** with full CRUD operations and relational modeling. Built as a capstone project to practice ERD design, JPA/Hibernate, and RESTful API development.

## Tech Stack
- **Language:** Java (17+)
- **Framework:** Spring Boot (3.x)
- **Database:** MySQL (8.x)
- **ORM:** JPA / Hibernate
- **Build:** Maven
- **Tools:** Git/GitHub, ARC (for HTTP requests)

## Features
- CRUD for **Peppers**, **Sauces**, **Ingredients**
- Many-to-many join between **Sauces** and **Ingredients**
- JSON REST endpoints with conventional plural paths
- Layered architecture (entities, repositories, controllers)

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8.x

### Setup
1. Create a MySQL database (example):
   ```sql
   CREATE DATABASE pepper_boss CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. Update `src/main/resources/application.yaml` (or `application.properties`) with your MySQL username/password.
3. (Optional) Seed sample data using `data.sql`.

### Run
```bash
mvn spring-boot:run
```
App defaults to `http://localhost:8080`.

## Example Endpoints (plural)
### Peppers
- `GET    /peppers`
- `GET    /peppers/{id}`
- `POST   /peppers`
- `PUT    /peppers/{id}`
- `DELETE /peppers/{id}`

### Sauces
- `GET    /sauces`
- `GET    /sauces/{id}`
- `POST   /sauces`
- `PUT    /sauces/{id}`
- `DELETE /sauces/{id}`

### Ingredients
- `GET    /ingredients`
- `GET    /ingredients/{id}`
- `POST   /ingredients`
- `PUT    /ingredients/{id}`
- `DELETE /ingredients/{id}`

*(If your actual controller paths differ, update these to match your code.)*

## Testing with ARC
Use ARC to send JSON requests. Example POST to create a pepper:
```http
POST /peppers
Content-Type: application/json

{
  "name": "Lemon Drop",
  "heatLevel": "Hot",
  "notes": "Bright, Citrusy"
}
```

## ERD (Overview)
**Peppers**, **Sauces**, **Ingredients**; **Sauce ↔ Ingredient** is many-to-many via a join table (e.g., `sauce_ingredient` with quantity/unit).

## Roadmap
- Pagination & filtering
- Basic auth / JWT
- Dockerized MySQL + app
- CI build (GitHub Actions)
- Deployed demo (Render/Railway/Fly)

## License
MIT © Garrett Newcomer — see [LICENSE](./LICENSE).
