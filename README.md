# Surest Member Management - Spring Boot Project

## How to run
1. Create a PostgreSQL database and user:
   ```sql
   CREATE DATABASE surestdb;
   CREATE USER surest_user WITH ENCRYPTED PASSWORD 'surest_pass';
   GRANT ALL PRIVILEGES ON DATABASE surestdb TO surest_user;
   ```

2. Update `src/main/resources/application.yml` if your DB credentials differ.

3. Build and run:
   ```bash
   ./gradlew bootRun
   ```

The application will start on port 8080. Flyway migrations create tables and roles. The application seeds two users (admin/user) at startup:
- admin / adminpass
- user / userpass

Use `/auth/login` to obtain JWT, then call `/members` endpoints with the `Authorization: Bearer <token>` header.

## Code coverage
Run `./gradlew clean test jacocoTestReport` and open `build/reports/jacoco/test/html/index.html` to view the report.
