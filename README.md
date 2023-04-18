# Spring Anti-Fraud-System

This project implements an anti-fraud system for the financial sector. It contains a REST API with several endpoints for interacting with users and an internal transaction validation system based on a set of heuristic rules. 

## Requirements

To build and run this project you need:

- [JDK 16+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)

## Get started

1. Clone this repository

```shell
git clone https://github.com/aldoushux503/Anti-Fraud-System.git
```

2. Setup the environment

Edit the application.properties file to configure the database settings. 
For H2 database, the default settings should be sufficient, but you can change them if needed.

```shell
spring.datasource.url=jdbc:h2:file:~/antifraud
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

#console settings
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.h2.console.settings.trace=false
spring.jpa.show-sql=true
```

_Alternatively, you can use an in-memory database [H2](https://www.h2database.com/html/main.html) by configuring
the `application.properties` files, just edit:_


```shell
spring.datasource.url=jdbc:h2:mem:antifraud
```

4. Build and run the project

```shell
mvn clean install
mvn spring-boot:run
```

## Endpoints

The endpoints can be accessed using a browser or a tool that allows you to send HTTP requests
like [Postman](https://www.getpostman.com/). There are several endpoints that you can use to interact with the system.
Request the according endpoint in a format shown in the examples below.

**Please note, that the first user you create will be an admin. This can only be changed in the database.**


|                                               | Anonymous | MERCHANT | ADMINISTRATOR | SUPPORT |
|-----------------------------------------------|-----------|----------|---------------|---------|
| POST /api/auth/user                           | +         | +        | +             | +       |
| DELETE /api/auth/user/{username}              | -         | -        | +             | -       |
| GET /api/auth/list                            | -         | -        | +             | +       |
| PUT /api/auth/role                            | -         | -        | +             | -       |
| PUT /api/auth/access                          | -         | -        | +             | -       |
| POST /api/antifraud/transaction               | -         | +        | -             | -       |
| POST, DELETE, GET api/antifraud/suspicious-ip | -         | -        | -             | +       |
| POST, DELETE, GET api/antifraud/stolencard    | -         | -        | -             | +       |
| GET /api/antifraud/history                    | -         | -        | -             | +       |
| PUT /api/antifraud/transaction                | -         | -        | -             | +       |

_'+' means the user with the role above can access that endpoint. '-' means the user with the role above does not have
access to that endpoint._

### Region codes

| Code | Region                           |
|------|----------------------------------|
| EAP  | East Asia and Pacific            |
| ECA  | Europe and Central Asia          |
| HIC  | High-Income countries            |
| LAC  | Latin America and the Caribbean  |
| MENA | The Middle East and North Africa |
| SA   | South Asia                       |
| SSA  | Sub-Saharan Africa               |

## Examples

You can find examples for all endpoints in the [usage examples](docs/USAGE.md) in the `docs` folder.

## Architecture

The system is built on a [Spring Framework](https://spring.io/) application context. The application itself follows the
model-view-controller pattern. The application consists of the following components:

- **Authentication**: The Authentication component is responsible for managing user authentication. It is responsible
  for validating user credentials and creating a session for the user.
- **Controller**: The Controller component is responsible for handling requests from the user.
- **Entity**: The Entity components are responsible for managing the different data models.
- **Repository**: The repository components are responsible for managing the data storage.
- **Service**: The service layer manages the main business logic.
- **Security**: The security layer is responsible for managing the access control and the authorization.
- **Util**: The utility layer contains various helper classes.

## Stack

- Java 16
- Maven
- Spring Boot 3.0.5
- H2 Database 

## Dependencies

- [Spring Boot 3.0.5](https://spring.io/projects/spring-boot)
- [Spring Boot Web Starter](https://spring.io/projects/spring-boot)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring Boot Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Boot Security](https://spring.io/projects/spring-security)
- [Hibernate Validator](https://hibernate.org/validator/)
- [H2 Database](https://www.h2database.com/)
- [Lombok](https://projectlombok.org/)

## Conclusion
This project demonstrates the principles of anti-fraud systems in the financial sector. It provides a starting point for building a more complete and robust system that can be tailored to specific business requirements.

