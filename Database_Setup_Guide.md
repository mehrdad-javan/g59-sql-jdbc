# Database Setup Guide: MySQL & PostgreSQL

This guide helps you set up a database for your Java applications. You can choose between **MySQL** or **PostgreSQL**, and you can install them **locally** or run them via **Docker**.

---

## Choosing Your Setup

Before you begin, decide which approach works best for you:

### 1. Which Database?
- **MySQL:** The most popular open-source database, widely used in web development.
- **PostgreSQL:** A powerful, advanced object-relational database known for reliability and complex features.
*Most beginners start with MySQL, but both are excellent choices.*

### 2. Local vs. Docker?
- **Local Installation:** Best if you want the database to run as a permanent service on your computer. It's slightly more complex to install but very stable.
- **Docker (Recommended):** Best for a quick, clean setup. It runs the database in a "container" without "polluting" your operating system with background services. You can start/stop it easily.

---

## Option A: MySQL Setup

### 1. Local Installation (Windows / macOS / Linux)
1. **Download & Install:** [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)  
2. **Configuration:** Set a **Root Password** (e.g., `root`).
3. **Management Tool:** Download [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) to manage your data.

### 2. Docker Setup
You can run it using a single command or a Docker Compose file.

#### A. Single Command
Run this command in your terminal:
```bash
docker run -d --name my-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -v mysql_data:/var/lib/mysql mysql:latest
```

#### B. Docker Compose (Alternative)
If you prefer using Docker Compose, you can create a `compose-mysql.yml` file and paste the configuration found at the bottom of this guide. Then run:
```bash
docker compose -f compose-mysql.yml up -d
```

**Explanation:**
- `-d`: Runs the container in the background (detached mode).
- `--name my-mysql`: Names your container.
- `-e MYSQL_ROOT_PASSWORD=root`: Sets the environment variable for the root password.
- `-p 3306:3306`: Maps port 3306 on your machine to port 3306 in the container.
- `-v mysql_data:/var/lib/mysql`: Creates a volume to persist your data even if the container is deleted.

---

## Option B: PostgreSQL Setup

### 1. Local Installation (Windows / macOS / Linux)
1. **Download:** Visit the [PostgreSQL Downloads](https://www.postgresql.org/download/) page.
2. **Install:** 
   - **Windows/macOS:** Use the interactive installer by EDB.
   - **Linux:** Use `sudo apt install postgresql`.
3. **Configuration:** The default user is `postgres`. Set a password during installation.
4. **Management Tool:** [pgAdmin](https://www.pgadmin.org/download/) is included in most installers.

### 2. Docker Setup
You can run it using a single command or a Docker Compose file.

#### A. Single Command
Run this command in your terminal:
```bash
docker run -d --name my-postgres -e POSTGRES_PASSWORD=root -p 5432:5432 -v postgres_data:/var/lib/postgresql/data postgres:latest
```

#### B. Docker Compose (Alternative)
If you prefer using Docker Compose, you can create a `compose-postgres.yml` file and paste the configuration found at the bottom of this guide. Then run:
```bash
docker compose -f compose-postgres.yml up -d
```

**Explanation:**
- `-e POSTGRES_PASSWORD=root`: Sets the password for the default `postgres` user.
- `-p 5432:5432`: PostgreSQL default port is 5432.
- `-v postgres_data...`: Persists your database files.

---

## Connection Details

Once your database is running, use these credentials to connect from your Java app or management tool:

### MySQL
- **Host:** `localhost`
- **Port:** `3306`
- **User:** `root`
- **Password:** (The one you set during setup)

### PostgreSQL
- **Host:** `localhost`
- **Port:** `5432`
- **User:** `postgres`
- **Password:** (The one you set during setup)

---

---

## Docker Compose Configurations

### MySQL Configuration
Create a file named `compose-mysql.yml` and paste the following content:

```yaml
services:
  mysql:
    image: mysql:latest
    container_name: my-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

### PostgreSQL Configuration
Create a file named `compose-postgres.yml` and paste the following content:

```yaml
services:
  postgres:
    image: postgres:latest
    container_name: my-postgres
    environment:
      POSTGRES_PASSWORD: root
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## Helpful Links for Beginners
- [Official MySQL Documentation](https://dev.mysql.com/doc/)
- [Official PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Hub - MySQL](https://hub.docker.com/_/mysql)
- [Docker Hub - PostgreSQL](https://hub.docker.com/_/postgres)
