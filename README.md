# Library Management System

A web-based Library Management System built with Spring Boot.

## Tech Stack
- Java 21
- Spring Boot 3.5
- Spring Web + Thymeleaf
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Gradle
- Railway (deployment)

## Project Structure
- Main app: `demo/`
- Backend code: `demo/src/main/java`
- Frontend templates: `demo/src/main/resources/templates`
- Static files: `demo/src/main/resources/static`

## Features
- Role selection (Admin/User)
- Admin login and admin dashboard
- User signup and user login
- Book, member, borrow, and return management

## Run Locally
### 1) Requirements
- Java 21
- PostgreSQL running locally

### 2) Configure database
Set DB values (optional if defaults already match your machine):
- `PGHOST`
- `PGPORT`
- `PGDATABASE`
- `PGUSER`
- `PGPASSWORD`

### 3) Start app
```bash
cd demo
./gradlew bootRun
```

App runs at: `http://localhost:8080`

## Deploy to Railway
### 1) Push this repository to GitHub

### 2) Create Railway project
- New Project -> Deploy from GitHub repo
- Set **Root Directory** to `demo` (important)

### 3) Add PostgreSQL service on Railway
- Attach PostgreSQL plugin/service to the project
- Ensure app service gets DB env vars (`PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`)

### 4) Build and start commands
Already configured in:
- `demo/nixpacks.toml`
- `demo/railway.toml`

Build:
```bash
./gradlew clean build -x check -x test -Pproduction --no-daemon
```

Start:
```bash
java $JAVA_OPTS -Dserver.port=$PORT -jar $(ls -1 build/libs/*.jar | grep -v plain | head -n 1)
```

## Database Notes
- Main auth table: `user_accounts`
- If data exists locally only, export/import to Railway PostgreSQL before demo.

## Author
- Student project for Library Management System submission.
