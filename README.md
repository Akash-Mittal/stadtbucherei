# Stadtbucherei
[![Build and Test](https://github.com/Akash-Mittal/stadtbucherei/actions/workflows/main.yml/badge.svg)](https://github.com/Akash-Mittal/stadtbucherei/actions/workflows/main.yml) 
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Akash-Mittal_stadtbucherei&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Akash-Mittal_stadtbucherei) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=Akash-Mittal_stadtbucherei)](https://sonarcloud.io/summary/new_code?id=Akash-Mittal_stadtbucherei)

---

## Java Web Application with PostgreSQL, Docker, OpenAPI, and Codefresh

For more details on the assignment, check the [Assignment Document](doc/assignment.md).

---

## Overview
**Stadtbucheri** is a library management application. This repository contains the backend services for managing loans, books, members, and more. The application is built using **Spring Boot** and **PostgreSQL**, and it can be run locally with or without **Docker Compose**.

---

#### Prerequisites:
- **Java 17+** should be installed on your local machine.
- **Gradle** for managing dependencies and running the application.
- Docker and Docker Compose should be installed on your machine. If they are not installed, follow the installation guides here:
  - [Install Docker](https://docs.docker.com/get-docker/)
  - [Install Docker Compose](https://docs.docker.com/compose/install/)


## How to Start Locally

### Without Docker
To run the application without Docker, simply set up the necessary dependencies, and you can access the Swagger UI for the API at:
./gradlew build
./gradlew bootRun -Dspring.profiles.active=h2
Access on 
  http://localhost:9091/swagger-ui/index.html#/

### With Springboot and Postgress Docker DB
./gradlew build
./gradlew bootRun -Dspring.profiles.active=postgres 
(make sure postgres is up, you can do that by starting a docker instance with)
docker-compose --profile db up -d
Access on 
http://localhost:9091/swagger-ui/index.html#/

---

### With Docker Compose

To run the application locally using Docker Compose, follow these steps:


#### Steps to Start:
* git clone https://github.com/Akash-Mittal/stadtbucherei
* cd stadtbucherei
* ./gradlew build
* docker-compose --profile full up -d
* http://localhost:9091/swagger-ui/index.html#/
   
### Assumptions
* Due to time constraints - no separate DB scripts are provided.
* The DDL is set to create, hence it will create schema every time.
* The Controller Integration tests are done with Docker TestContainers.(However full tests are not done due time and memory constraints.)
* Limited validations, error handling and edge cases testing, all test cases are not provided.
* Exception chaining from repo to controller is not done.
* The response entity for each controller is not provided.
* Test Code Coverage > 80% , the Jacoco is not configured to ignore the pojos and repos.
* Code coverage report can be accessed in build/lib
* Code quality is done via sonarqube on the cloud - please click the banner to see the open issues.
* The code is also built via github actions (there is further scope to deploy the whole docker, its not delivered)
* The documentation has more focus on code as documentations with Swagger api documentation.
* no env file created, and many properties are not externalized.
* No cascading in related entities 


 
 
### Key technologies 
* Spring data
* JPA 
* Test Containers for IT testing - full stack testing 
* H2 database for testing.
* SonarQube for Code Quality.
* Jacoco for Code Quality.
* OPEN API/ Swagger.
* Docker


### References
* [Testcontainers](https://www.testcontainers.org/)
* [Postgres in Docker](https://hub.docker.com/_/postgres) 

   