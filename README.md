# stadtbucherei
[![Build and Test](https://github.com/Akash-Mittal/stadtbucherei/actions/workflows/main.yml/badge.svg)](https://github.com/Akash-Mittal/stadtbucherei/actions/workflows/main.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Akash-Mittal_stadtbucherei&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Akash-Mittal_stadtbucherei)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=Akash-Mittal_stadtbucherei)](https://sonarcloud.io/summary/new_code?id=Akash-Mittal_stadtbucherei)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=Akash-Mittal_stadtbucherei)

<img src="doc/stadtbucherei.png" alt="stadtbucherei" width="600" />


# Java Web Application with PostgreSQL, Docker, OpenAPI and Codefresh

## Overview

This project involves creating a Java web application using Java 17+ and Gradle, with one of the following frameworks: Spring, Micronaut, or Quarkus. The application should provide OpenAPI documentation and an interactive Swagger-UI page. PostgreSQL will be used for data storage, and the application will be delivered as a Docker image and/or using Docker Compose. The focus is on proper separation of concerns, a well-structured package layout, and a suitable architecture, such as a classical layered approach or hexagonal architecture.

## Requirements

- **Java Application**: Build a Java application that follows best practices with a chosen framework, providing a CRUD REST API.
- **Gradle**: Use Gradle for dependency management.
- **OpenAPI**: Document all possible REST interactions.
- **PostgreSQL Database**: Use PostgreSQL for local data storage.
- **Docker**: Deliver the application as a Docker image and/or use Docker Compose.

## Context

- **Book**: A book has a title, a genre, a price, and exactly one author. (The library has only one copy of each book.)
- **Author**: An author has a name, a date of birth, and can have multiple books.
- **Member**: A member has a unique username, an email, an address, and a phone number.
- **Loan**: A member can loan up to 5 different books at a time. Each loan has a lend date and a return date (consider only whole days, no timestamps).

## Tasks

### 1. Set Up the Java Application
- Initialize a new Java project using an initializer, template, or from scratch.
- Set up Gradle for dependency management, including necessary dependencies and plugins.

### 2. Integrate REST and OpenAPI
- Include OpenAPI and provide a Swagger-UI accessible from the base URL of the application.
- Implement CRUD endpoints for `Book`, `Author`, `Member`, and `Loan`, ensuring appropriate error responses.

### 3. Set Up PostgreSQL Database
- Set up a local PostgreSQL database using Docker.
- Create tables reflecting the entities and relationships described above.

### 4. Implement Backend Functionality
- Implement all REST CRUD endpoints for `Book`, `Author`, `Member`, and `Loan`.
- Ensure proper constraints and relationships (e.g., unique constraints, relationships between entities).

### 5. Framework Usage and Architecture
- Leverage the framework and additional libraries as necessary.
- Follow best practices for project layout and structure code according to a suitable software architecture.

### 6. Dockerization
- Create a `Dockerfile` to containerize the Java application.
- Set up `docker-compose.yml` to manage the Java application and PostgreSQL as services.
- Ensure the application can be run with a single `docker-compose up` command.

## Deliverables

1. **Source Code**: Java application code with Gradle configurations.
2. **PostgreSQL Schema**: Database schema and any SQL scripts (if not using embedded migrations).
3. **Dockerfile**: File to containerize the Java application.
4. **Docker Compose**: `docker-compose.yml` to run all services.
5. **Documentation**: README.md with instructions to build and run the application with Docker.

## Evaluation Criteria

- **Functionality**: Does the application meet all the specified requirements?
- **Code Quality**: Is the code correct, well-structured, and easy to understand?
- **Clean Code**: Are clean code practices followed?
- **CRUD Endpoints**: Are the REST API endpoints integrated correctly and efficiently?
- **Database Integration**: Is PostgreSQL used appropriately for storing data?
- **Framework Usage**: Are the core utilities of the chosen framework leveraged?
- **Software Architecture**: Does the architecture suit the project?
- **Dockerization**: Does the Docker setup work smoothly, and could it be used in Kubernetes?
- **Documentation**: Are the instructions clear and complete?

## Additional Notes

- Include necessary environment variables in a `.env` file and reference them correctly in the Docker setup.
- Use meaningful commit messages and structure the Git history logically if submitting via a Git repository.
- Handle edge cases and error handling to ensure the robustness of the application.
- If certain requirements are unclear or missing, make assumptions and document them accordingly.

### Key technologies 
* Spring data
* JPA / Hibernate / ORM
* Flyway
* Test Containers for IT testing - full stack testing 
* H2 database for testing - the old way :)

### References
* [Testcontainers](https://www.testcontainers.org/)
* [Postgres in Docker](https://hub.docker.com/_/postgres) 

### Swagger UI
http://localhost:9090/swagger-ui/index.html#/

