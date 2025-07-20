Spring Boot Event Listener Project
Overview
This Spring Boot application demonstrates an event-driven architecture using ApplicationEventPublisher with @EventListener and @TransactionalEventListener to handle domain events in a transaction-safe and decoupled manner. The primary use case is user registration, where a notification (simulated by a console log) is triggered after a user is successfully saved to the database. The project compares @EventListener (synchronous, non-transactional) and @TransactionalEventListener (transaction-bound) to highlight their differences and optimal use cases.
Key Features:

Event-Driven Design: Decouples user registration from notification logic using Spring’s ApplicationEventPublisher.
Flexible Event Handling: Supports both @EventListener for synchronous tasks and @TransactionalEventListener for transaction-safe actions.
Clean Architecture: Organizes code in a modular, reusable structure.
Asynchronous Support: Optionally uses @Async for non-blocking notification processing.

Project Structure
com.example.demo
├── controller
│   └── UserController.java
├── model
│   └── User.java
├── repository
│   └── UserRepository.java
├── service
│   └── UserService.java
├── event
│   ├── UserRegisteredEvent.java
│   └── NotificationListener.java
└── DemoApplication.java


controller/: REST endpoints for user interaction.
model/: JPA entities (e.g., User).
repository/: Spring Data JPA repositories for database operations.
service/: Business logic for user operations.
event/: Event-related classes, including event models and listeners.

Prerequisites

Java: 17 or later
Spring Boot: Version 3.x
Maven or Gradle: Build tool for dependency management
Database: H2 (for testing) or any JPA-compatible database (e.g., MySQL, PostgreSQL)
IDE: IntelliJ IDEA, Eclipse, or any IDE with Lombok support

Setup Instructions

Clone the Repository:
git clone <repository-url>
cd <repository-directory>


Add Dependencies:Ensure the following dependencies are in your pom.xml (for Maven):
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>


Configure Application:Add the following to src/main/resources/application.properties:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update


Enable Async Support:Ensure the main application class includes @EnableAsync:
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}


Build and Run:
mvn clean install
mvn spring-boot:run



Usage
The application exposes a REST endpoint to trigger user registration:

Endpoint: POST /api/users

Request Body:
{
    "name": "Piyush",
    "email": "piyush@example.com"
}


Example Request:
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"Piyush","email":"piyush@example.com"}'


Response:
{
    "id": 1,
    "name": "Piyush",
    "email": "piyush@example.com"
}


Console Output:
📝 [Sync] Logging user registration: Piyush
📧 [Transactional] Sending welcome email to: piyush@example.com



Implementation Details
The application uses Spring’s event-driven framework to handle user registration events:

User Registration:

The UserService saves a user to the database within a @Transactional method and publishes a UserRegisteredEvent.
The @Transactional annotation ensures database operations and event publishing are atomic.


Event Handling:

@EventListener: Logs the event synchronously within the transaction (e.g., for debugging or non-critical tasks).
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT): Triggers a notification (e.g., email simulation) only after the transaction commits, ensuring no side effects occur if the transaction fails.
@Order: Controls execution order (logging before notification).


Asynchronous Processing:

Listeners can be made non-blocking with @Async, improving API response times.



Key Components

User.java: JPA entity representing a user with id, name, and email.
UserRegisteredEvent.java: POJO carrying the User object for event handling.
UserRepository.java: Spring Data JPA repository for CRUD operations.
UserService.java: Business logic for user registration and event publishing.
NotificationListener.java: Handles events with @EventListener (logging) and @TransactionalEventListener (notification).
UserController.java: REST endpoint to trigger user registration.

Comparing @EventListener and @TransactionalEventListener



Feature
@EventListener
@TransactionalEventListener



Execution Timing
Immediate, within transaction
After transaction commits (default: AFTER_COMMIT)


Transaction Dependency
Works without transaction
Requires active transaction


Safety for Side Effects
Risky if transaction rolls back
Safe, runs only after successful commit


Use Case
Internal logging, non-critical tasks
Notifications, external actions


Performance
Synchronous, may block
Can be async with @Async


Benefits

Decoupled Logic: Separates user registration from notification logic.
Transaction Safety: Ensures notifications are triggered only after successful commits.
Flexibility: Supports synchronous (@EventListener) and transaction-bound (@TransactionalEventListener) handling.
Scalability: Easily add more listeners without modifying core logic.
Reusability: Event-driven pattern reusable for other entities/events.
Performance: Async listeners improve response times.

Potential Extensions

Asynchronous Notifications: Add @Async to listeners for non-blocking processing.
Multiple Listeners: Add listeners for analytics or logging with @Order.
Error Handling: Implement fallback logic for notification failures.
Rollback Handling: Add @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK) for rollback scenarios.
Real Email Integration: Replace console logs with Spring Mail.

Sample Output
Request:
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"Piyush","email":"piyush@example.com"}'

Console Output:
📝 [Sync] Logging user registration: Piyush
📧 [Transactional] Sending welcome email to: piyush@example.com

Response:
{
    "id": 1,
    "name": "Piyush",
    "email": "piyush@example.com"
}

Rollback Scenario (if UserService throws an exception):

@EventListener: May execute (logs appear).
@TransactionalEventListener: Does not execute (no email sent).

Conclusion
This project provides a robust, scalable, and transaction-safe way to handle domain events in Spring Boot. The combination of @EventListener and @TransactionalEventListener offers flexibility for both synchronous and transaction-bound tasks. The clean architecture ensures maintainability and reusability, making it ideal for monolithic or microservices applications.
Next Steps:

Integrate with a real email service (e.g., Spring Mail).
Add more listeners for additional functionality (e.g., analytics).
Extend to handle other events (e.g., UserDeletedEvent).
