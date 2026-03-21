# Laboratory 7

> [!NOTE]
> Since this lab depends in lab-6 somehow, you may notice a few changes in the structure/classes/UMLs
> of this version, this is due to the changes made since in the lab-6, we were working on a early stage
> of the project; now, in this version, we have the final result

**UPDATES FROM LABORATORY 6**

In the previous lab, we implemented some basic classes and tests in order to use JUnit
and understand better the concept of unit tests. This coding section is available in
the `tech_cup` folder, where the `main` folder contains the suggested folders and the
test on contains the test classes. In addition, the initial Class Diagram is available
in the folder called *Astah - Class Diagram*, in case you want to take a look. 

In order to keep the suggested structured for the Laboratory 7, the classes done previously
are inside the `main`, in a folder called `lab6`. This folder has several packages for the
different classes, following the structure shown in the class diagram. 

---

## SpringBoot Questions

### 1. What is the purpose of the Controller package in a Spring Boot structure?

The Controller package contains the classes used for handling HTTP requests from clients like browsrers, front-end applications, or API clients. 

The controllers do the following: 

- Define REST endpoints like users, products, etc.
- Receive request data (GET, POST, PUT, DELETE).
- Call the Service layer to execute business logic. 
- Return an HTTP response to the client (usually JSON).

Some annotations used are:
- `@RestController`
- `@RequestMapping`
- `@GetMapping`
- `@PostMapping`

**Example:**

```java
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
```

### 2. What is the purpose of the Service package in a Spring Boot structure?

This package contains the business logic of the application.

It implements the main operations of the system, such as:

- Validating data. 
- Applying business rules.
- Processing information. 
- Coordinating calls tro repositories. 

Services:

- Receive requests from the Controller
- Interact with Repositories
- Return results to the Controller

They typically use the annotation: `@Service`

**Example:**

```java
@Service
public class UserService {

    public List<User> getUsers(){
        // business logic
    }
}
```

### 3. What is the purpose of the Repository package in a Spring Boot structure?

The Repository package is responsible for interacting with the database.

Repositories:

- Perform CRUD operations
- Communicate with the database
- Allow saving, retrieving, updating, and deleting data

They usually extend interfaces such as:

- `JpaRepository`
- `CrudRepository`

**Example**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// something
}
```

### 4. What is the purpose of the Controller package in the Spring Boot structure?

The Controller package represents the presentation layer or API layer of the application.

Its main responsibilities are:

- Exposing API endpoints
- Receiving client requests
- Returning HTTP responses
- Delegating business operations to the Service layer

In the MVC architecture, this corresponds to the Controller layer.

### 5. What is the purpose of the Entity package in a Spring Boot structure?

The Entity package contains classes that represent database tables.

Each entity:

- Corresponds to a database table
- Each attribute corresponds to a column
- Is used for data persistence

Entities usually use annotations such as:

- `@Entity`
- `@Id`
- `@GeneratedValue`
- `@Column` 

**Example**

```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
```

### 6. What is the purpose of the DTO package in a Spring Boot structure?

DTO stands for Data Transfer Object.

DTOs are used to transfer data between layers or between the backend and the client without exposing entities directly.

They help to:

- Control what data is sent to the client
- Avoid exposing the internal database structure
- Improve security
- Reduce unnecessary data in responses

A DTO typically contains only the data required for a specific operation.

**Example**

```java
public class UserDTO {
    private String name;
    private String email;
}
```

### 7. What is the purpose of the Exception package in a Spring Boot structure?

The Exception package contains classes used for custom error handling in the application.

It helps to:

- Define custom exceptions
- Handle errors in a centralized way
- Return appropriate HTTP responses (404, 400, 500)

It is often used together with annotations such as:

- `@ExceptionHandler`
- `@ControllerAdvice`

**Example**

```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }
}
```

This allows developers to manage application errors in a more organized and readable way.

### REFRENCES (APA)

- Richardson, C. (2018). Microservices patterns: With examples in Java. Manning Publications.
- Walls, C. (2022). Spring Boot in action (2nd ed.). Manning Publications.
- [Spring. (2024). Spring Boot reference documentation.](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Baeldung. (2024). Spring Boot architecture.](https://www.baeldung.com/spring-boot-architecture)

---

## 📸 Results and Evidence

![tests](./images/lab-7-tests.png)

In this laboratory, a REST API was implemented for the first cycle, including the development of controllers, services, and business logic for tournament management. Additionally, Swagger (OpenAPI) was integrated to provide interactive API documentation, allowing visualization and testing of endpoints. The application was successfully compiled and executed, and the API was verified through the local server.

### Application Running (Localhost)
![P1](./images/lab-7-p1.png)
![P2](./images/lab-7-p2.png)
![P3](./images/lab-7-p3.png)

### Build and Compilation
![Swagger](./images/lab-7-swagger.png)
