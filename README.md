# Application Documentation

## **1. Introduction**

*Briefly describe the purpose of the application and its main features. Mention any external dependencies or frameworks used.*

This Restaurant Review project is an API designed to allow users to discover, rate, and review restaurants. The main objective of this API is to provide a simple and efficient way to find the best dining experiences while helping restaurant owners gather valuable feedback to improve their services.

Main features of the API include:

1. User registration and authentication: Users can sign up for an account and securely log in to access the API's features.
2. Browsing restaurants: Users can view a list of restaurants, filter them by various criteria such as city and rating.
3. Restaurant details: Users can view detailed information about a specific restaurant, including its location and reviews.
4. Rating and reviewing restaurants: Users can rate and write reviews for restaurants they've visited, providing valuable feedback for both restaurant owners and other users.
5. Restaurant management: Restaurant owners or admins can add, update, and delete restaurant listings.

The API is built using the Spring Boot framework, which is a widely-used Java-based framework for creating scalable and maintainable applications. The backend handles all the necessary data processing, storage, and management, allowing it to be consumed by various types of clients, such as web or mobile applications.

Some of the key external dependencies and frameworks used in the application include:

1. Spring Boot: Provides a simplified way to develop and configure Spring applications, along with a built-in web server for easy deployment.
2. Spring Security: Offers comprehensive security services for Java applications, including authentication, authorization, and protection against common vulnerabilities.
3. Spring Data JPA: Simplifies data access by providing an abstraction layer over the Java Persistence API (JPA) and enabling easy integration with various databases.
4. Hibernate: Acts as the Object-Relational Mapping (ORM) implementation for JPA, allowing seamless mapping of Java objects to database tables.
5. OpenAPI: Offers a standardized method for describing and documenting RESTful APIs, making it easier for developers and users to understand the API's capabilities.

## 2. Prerequisites
*List the required software and tools to build, run, and test the application. Include version numbers, if necessary.*
1. Java Development Kit (JDK) version 17 or later
2. Maven build tool version 3.6.0 or later
3. IDE (Integrated Development Environment) such as IntelliJ IDEA
4. Web browser (such as Google Chrome or Mozilla Firefox) for testing the API endpoints
5. Testing framework such as JUnit
6. Spring Boot version 3.0.4 or later
7. H2 Database Engine version 1.4.200 or later (used for local development and testing)
8. Spring Security version 5.5.2 or later
9. OkHttp version 4.10.0 or later (used for making HTTP requests in tests)
10. Lombok version 1.18.20 or later (optional, used for generating boilerplate code)
11. Jackson Databind Nullable version 0.2.6 or later
12. OpenAPI Generator Maven Plugin version 4.3.1 or later (used for generating REST API client code)
13. MapStruct version 1.4.2.Final or later (used for object mapping)
14. Gson version 2.10.1 or later (used for JSON serialization and deserialization)
15. JsonPath version 2.7.0 or later (used for testing JSON responses)
16. Swagger Annotations version 2.2.8 or later.
---

## 3. Setup and Installation
*Provide step-by-step instructions for setting up the development environment. Describe how to install dependencies, configure the application, and run it locally.*

Here are the general steps to set up and run the application locally:

1. Install Java 17
    - You can download and install Java 17 from the official website: **[https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)**

2. Install Maven
    - Maven is a build automation tool used to manage dependencies and build the project. You can download and install Maven from the official website: **[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)**

3. Clone the project repository
    - You can clone the project repository using Git command line tool.

    ```
    git clone https://github.com/username/repository.git
    ```

4. Build the project
    - Navigate to the project root directory and run the following command to build the project.
    - This command will download all the project dependencies, build the project and generate an executable jar file.

    ```
    mvn clean install
    ```

5. Run the application
    - After building the project, you can run the application by executing the generated jar file. The jar file should be located in the "target" directory.

    ```
    java -jar target/restaurant-review-0.0.1-SNAPSHOT.jar
    ```

    - The application should be up and running on **[http://localhost:8080](http://localhost:8080/)**.

---

## 4. Application Architecture
*Give an overview of the main components and layers of the application. Include a simple diagram illustrating the architecture.*

The Restaurant Review app is made with the Spring Boot framework and follows a layered architecture pattern, consisting of four layers: presentation, service, repository, and database.

The presentation layer is in the **`controller`** package, which exposes REST APIs for the app. The service layer is in the **`service`** package and handles the app's business logic. It communicates with the repository layer in the **`repository`** package to retrieve and manipulate data. The repository layer communicates with the database layer, which uses an in-memory H2 database and is managed using Spring Data JPA.

The entity layer has three entities that map to the database tables: **`RestaurantEntity`**, **`ReviewEntity`**, and **`UserEntity`**. The repository layer has three repositories: **`RestaurantRepository`**, **`ReviewRepository`**, and **`UserRepository`**, which provide methods for basic CRUD operations on its corresponding entity.

The service layer has three services: **`RestaurantService`**, **`ReviewService`**, and **`UserService`**, which interact with the repository layer to retrieve and manipulate data from the database. The controller layer has three controllers: **`RestaurantController`**, **`ReviewController`**, and **`UserController`**, which expose RESTful endpoints for clients to interact with the app.

Finally, the mapper layer provides mapping between entities and DTOs, used by the controller layer. The app is structured so that the client makes requests to the controller layer, which delegates to the service layer to perform business logic. The service layer interacts with the repository layer to perform CRUD operations on the database. The entities and DTOs are used by the controller and mapper layers to represent data.

---

## 5. Code Structure
*Briefly describe each package and its purpose. Explain the main classes and interfaces within each package. Provide guidance on naming conventions, directory structures, and other best practices followed in the codebase.*

**Naming Conventions:** Class names should be in `PascalCase`, describing their purpose. Method names should be in `camelCase` and start with a verb.

---
### **Package: `dev.drew.restaurantreview.auth`**
**Purpose:** This package contains classes related to authentication and authorization for the application. It includes the **`ApplicationConfig`** class, which sets up the authentication provider and manager, as well as the password encoder bean.

**Directory Structure:** Authentication and authorization classes should be placed in an **`auth`** package within the application.

**Class: `ApplicationConfig`**
- **Description:** This class configures the authentication provider, manager, and password encoder for the application. It sets up a **`DaoAuthenticationProvider`** using the **`JpaUserDetailsService`** for user details and the **`BCryptPasswordEncoder`** for password encoding.

**Class: `AuthenticationController`**
- **Description:** This class is a REST controller that handles authentication requests for the application. It maps the **`POST /api/v1/auth/authenticate`** endpoint to the **`authenticate`** method, which delegates the authentication process to the **`AuthenticationService`**.

**Class: `AuthenticationRequest`**
- **Description:** This class is a data model representing an authentication request. It contains the **`username`** and **`password`** fields, which are used to authenticate a client. The class is annotated with Lombok annotations for generating getters, setters, constructors, and a builder.

---
### **Package: `dev.drew.restaurantreview`**
**Purpose:** The dev.drew.restaurantreview package contains all the classes that are part of the restaurant review application. This package serves as the top-level package for the application.

**Class: `BasicDiningApplication`**
- **Description:** This class serves as the entry point for the Spring Boot application.

---

### Package: `dev.drew.restaurantreview.config`
**Purpose:** This package contains configuration classes for the application. In this case, it includes the `SecurityConfig` class, which configures the Spring Security settings for the application.

**Directory Structure:** Configuration classes should be placed in a `config` package within the application.

**Class: `SecurityConfig`**
- **Description:** This class configures the security settings for the application, such as authentication and authorization rules. It sets up basic authentication, allowing certain HTTP requests to be accessible without authentication and requiring authentication for the rest.

---

### Package: `dev.drew.restaurantreview.controller`
**Purpose:** The controller package is responsible for handling incoming requests from clients and returning responses.

**Directory Structure:** The directory structure for controllers should follow the package structure of the application and be placed in a directory named `controller`.

**Class: `RestaurantController`**
- **Description:** This class is responsible for handling requests related to restaurants. The `RestaurantController` class is a Spring RestController that implements the `RestaurantsApi` interface. It contains methods that map to different endpoints for handling HTTP requests related to restaurants. The class is annotated with `@RestController` and `@RequestMapping` which marks it as a controller and maps all endpoints within the class to the root URL of the application. Additionally, the `@PreAuthorize` annotation is used to restrict access to some endpoints based on user authentication status.

**Class: `ReviewController`**
- **Description:** This class is responsible for handling requests related to reviews. The **`ReviewController`** class is a Spring RestController that implements the **`ReviewsApi`** interface. It contains methods that map to different endpoints for handling HTTP requests related to reviews. The class is annotated with **`@RestController`**, **`@RequestMapping`**, and **`@PreAuthorize`**, which marks it as a controller and maps all endpoints within the class to the root URL of the application. Additionally, the **`@PreAuthorize`** annotation is used to restrict access to some endpoints based on user authentication status.
  
**Class: `UserController`**
- **Description:** This class is responsible for handling requests related to users. The **`UserController`** class is a Spring RestController that implements the **`UserApi`** interface. It contains methods that map to different endpoints for handling HTTP requests related to users. The class is annotated with **`@RestController`**, **`@RequestMapping`**, and **`@PreAuthorize`**, which marks it as a controller and maps all endpoints within the class to the root URL of the application. Additionally, the **`@PreAuthorize`** annotation is used to restrict access to some endpoints based on user authentication status.

---

### **Package: `dev.drew.restaurantreview.entity`**
**Purpose:** This package contains the entity classes representing the data model objects in the application. These classes are mapped to the database tables using JPA and Hibernate.

**Directory Structure:** Entity classes should be placed in an **`entity`** package within the application.

**Class: `RestaurantEntity`**
- **Description:** This class represents a restaurant in the application. It extends the **`org.openapitools.model.Restaurant`** class and includes JPA annotations to map the class to the **`restaurant`** table in the database.
    - **`@Entity`**: Specifies that the class is an entity and is mapped to a database table.
    - **`@Table(name = "restaurant")`**: Specifies the name of the table to which this entity class is mapped.
    - **`@Data`**: A Lombok annotation that generates getters and setters for all fields, as well as **`equals`**, **`canEqual`**, **`hashCode`**, and **`toString`** implementations.
    - **`@NoArgsConstructor`**: A Lombok annotation that generates a no-argument constructor.
    - **`@AllArgsConstructor`**: A Lombok annotation that generates a constructor with all fields as arguments.
- **Fields:**
    - **`id`**: The primary key for the restaurant table. It is autogenerated using the **`GenerationType.IDENTITY`** strategy.
    - **`name`**: The name of the restaurant. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`city`**: The city where the restaurant is located. It is also marked with **`@NotNull`**.
    - **`userEntity`**: The the user who wrote the review. It is a many-to-one relationship with the `UserEntity` class.
    - **`rating`**: The average rating of the restaurant. It is marked with **`@NotNull`**, **`@Min(1)`** and **`@Max(5)`** to ensure that it must have a value between 1 and 5.
    - **`createdAt`**: The date and time when the restaurant was created, stored as an **`OffsetDateTime`**. It is annotated with **`@DateTimeFormat`** to specify the date-time format.

**Class: `ReviewEntity`**
- **Description:** This class represents a review in the application. It extends the **`org.openapitools.model.Review`** class and includes JPA annotations to map the class to the **`review`** table in the database.
    - **`@Entity`**: Specifies that the class is an entity and is mapped to a database table.
    - **`@Table(name = "review")`**: Specifies the name of the table to which this entity class is mapped.
    - **`@Data`**: A Lombok annotation that generates getters and setters for all fields, as well as **`equals`**, **`canEqual`**, **`hashCode`**, and **`toString`** implementations.
    - **`@NoArgsConstructor`**: A Lombok annotation that generates a no-argument constructor.
    - **`@AllArgsConstructor`**: A Lombok annotation that generates a constructor with all fields as arguments.
- **Fields:**
    - **`id`**: The primary key for the review table. It is autogenerated using the **`GenerationType.IDENTITY`** strategy.
    - **`restaurantEntity`**: The the restaurant being reviewed. It is marked with **`@NotNull`** to ensure that it must have a value. It is a many-to-one relationship with the `RestaurantEntity` class.
    - **`userEntity`**: The the user who wrote the review. It is a many-to-one relationship with the `UserEntity` class.
    - **`rating`**: The rating of the review. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`comment`**: The comment text of the review. It is also marked with **`@NotNull`**.
    - **`createdAt`**: The date and time when the review was created, stored as an **`OffsetDateTime`**. It is annotated with **`@DateTimeFormat`** to specify the date-time format.
    - **`updatedAt`**: The date and time when the review was last updated, stored as an **`OffsetDateTime`**. It is also annotated with **`@DateTimeFormat`**.

**Class: `UserEntity`**
- **Description:** This class represents a user in the application. It extends the **`org.openapitools.model.User`** class and includes JPA annotations to map the class to the **`users`** table in the database.
    - **`@Entity`**: Specifies that the class is an entity and is mapped to a database table.
    - **`@Table(name = "users", uniqueConstraints = {...})`**: Specifies the name of the table to which this entity class is mapped, along with unique constraints for the **`username`** and **`email`** columns.
    - **`@Data`**: A Lombok annotation that generates getters and setters for all fields, as well as **`equals`**, **`canEqual`**, **`hashCode`**, and **`toString`** implementations.
    - **`@NoArgsConstructor`**: A Lombok annotation that generates a no-argument constructor.
    - **`@AllArgsConstructor`**: A Lombok annotation that generates a constructor with all fields as arguments.
- **Fields:**
    - **`id`**: The primary key for the user table. It is autogenerated using the **`GenerationType.IDENTITY`** strategy.
    - **`name`**: The name of the user. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`email`**: The email address of the user. It is marked with **`@NotNull`** and **`@Email`** to ensure that it must have a value and be a valid email address.
    - **`username`**: The username of the user. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`password`**: The password of the user. It is also marked with **`@NotNull`**.
    - **`createdAt`**: The date and time when the user was created, stored as an **`OffsetDateTime`**. It is annotated with **`@DateTimeFormat`** to specify the date-time format.
    - **`role`**: The role of the user. It is marked with **`@NotNull`** to ensure that it must have a value. The **`RoleEnum`** is used to represent the different roles a user can have.

**Class: `SecurityUser`**
- **Description:** This class represents a security user in the application, which is used by the Spring Security framework to manage authentication and authorization. It implements the **`UserDetails`** interface and wraps a **`UserEntity`** object.
- **Methods:**
    - **`getAuthorities()`**: Returns a collection of granted authorities for the user, which represent their roles in the application. In this case, it returns a list containing a **`SimpleGrantedAuthority`** object with the user's role.
    - **`getPassword()`**: Returns the password of the user.
    - **`getUsername()`**: Returns the username of the user.
    - **`getId()`**: Returns the ID of the user.
    - **`isAccountNonExpired()`**: Returns **`true`**, indicating that the user's account is not expired.
    - **`isAccountNonLocked()`**: Returns **`true`**, indicating that the user's account is not locked.
    - **`isCredentialsNonExpired()`**: Returns **`true`**, indicating that the user's credentials are not expired.
    - **`isEnabled()`**: Returns **`true`**, indicating that the user's account is enabled.
    - **`getUserEntity()`**: Returns the **`UserEntity`** object wrapped by this **`SecurityUser`** object.
    - **`hasRole(String role)`**: Checks if the user has the specified role by comparing it with the authorities granted to the user.

---

### **Package: `dev.drew.restaurantreview.mapper`**
**Purpose:** The dev.drew.restaurantreview.mapper package contains all the classes responsible for mapping entities to API data transfer objects and vice versa. This package serves as the data transfer object mapping layer between the API and the entity layer.

**Directory Structure:** Mapping classes should be placed in a mapper package within the application.

**Interface: `RestaurantMapper`**
- **Description:** This interface defines the methods for mapping between the **`RestaurantEntity`**, **`Restaurant`**, and **`RestaurantInput`** classes. It is an interface annotated with **`@Mapper(componentModel = "spring")`**, which indicates that it is a MapStruct mapper with Spring integration.

**Interface: `ReviewMapper`**
- **Description:** This interface defines the methods for mapping between the **`ReviewEntity`**, **`Review`**, and **`ReviewInput`** classes. It is an interface annotated with **`@Mapper(componentModel = "spring")`**, which indicates that it is a MapStruct mapper with Spring integration.

**Interface: `UserMapper`**
- **Description:** This interface defines the methods for mapping between the **`UserEntity`**, **`User`**, and **`UserInput`** classes. It is an interface annotated with **`@Mapper(componentModel = "spring")`**, which indicates that it is a MapStruct mapper with Spring integration.

---

### **Package: `dev.drew.restaurantreview.repository`**
**Purpose:** This package contains interfaces for interacting with the database to manage restaurant review objects. Each interface defines a Spring Data JPA repository that extends JpaRepository, which provides basic CRUD operations. Additionally, the interfaces include custom methods for querying and deleting objects based on specific criteria.

**Directory Structure:** Repository interfaces should be placed in a repository package within the application.

**Interface: `RestaurantRepository`**
- **Description:** This interface defines the **`RestaurantRepository`**, which is responsible for interacting with the database to manage **`RestaurantEntity`** objects. It extends **`JpaRepository`** and is annotated with **`@Repository`**, indicating that it is a Spring Data JPA repository.

**Interface: `ReviewRepository`**
- **Description:** This interface defines the **`ReviewRepository`**, which is responsible for interacting with the database to manage **`ReviewEntity`** objects. It extends **`JpaRepository`** and is annotated with **`@Repository`**, indicating that it is a Spring Data JPA repository.
  
**Interface: `UserRepository`**
- **Description:** This interface defines the **`UserRepository`**, which is responsible for interacting with the database to manage **`UserEntity`** objects. It extends **`JpaRepository`** and is annotated with **`@Repository`**, indicating that it is a Spring Data JPA repository. It includes custom methods for finding and deleting users by their username.

---

### **Package: `dev.drew.restaurantreview.service`**
**Purpose:** This package contains the service layer classes responsible for the business logic of the application, such as interacting with repositories, mapping between model objects, and handling exceptions.

**Directory Structure:** Service classes should be placed in a **`service`** package within the application.

**Class: `JpaUserDetailsService`**
- **Description:** This class implements the **`UserDetailsService`** interface and provides a way to load user-specific data by their username for the authentication process. It has a **`loadUserByUsername()`** method, which retrieves user information from the **`UserRepository`**. It uses the **`SecurityUser`** class to return user details. The class is annotated with **`@Service`**, indicating that it is a Spring service component.

**Interface: `RestaurantService`**
- **Description:** This interface provides a set of methods for managing restaurants, including adding new restaurants, retrieving all restaurants with optional filters, getting a specific restaurant by ID, updating a restaurant by ID, and deleting a restaurant by ID. The actual implementation of these methods should be provided in a class implementing this interface.

**Class: `RestaurantServiceImpl`**
- **Description:** This class implements the **`RestaurantService`** interface and is responsible for handling business logic related to restaurants. It interacts with the **`RestaurantRepository`** and **`ReviewRepository`**, as well as the **`RestaurantMapper`**. The class is annotated with **`@Service`**, which marks it as a Spring service bean.

**Interface: `ReviewService`**
- **Description:** This interface provides a set of methods for managing reviews, including adding new reviews, retrieving all reviews with optional filters, getting a specific review by ID, updating a review by ID, and deleting a review by ID. The actual implementation of these methods should be provided in a class implementing this interface.

**Class: `ReviewServiceImpl`**
- **Description:** This class provides the implementation of the **`ReviewService`** interface, managing the functionality for managing reviews, including adding new reviews, retrieving all reviews with optional filters, getting a specific review by ID, updating a review by ID, and deleting a review by ID.

**Interface: `UserService`**
- **Description:** This interface defines the contract for managing user-related operations, such as adding a new user, getting a user by their ID, deleting a user by their ID, and updating a user by their ID.

**Class: `UserServiceImpl`**
- **Description:** This class implements the **`UserService`** interface and provides the logic for managing user-related operations, such as adding a new user, getting a user by their ID, deleting a user by their ID, and updating a user by their ID.

---
### Package: **`dev.drew.restaurantreview.util`**
**Purpose:** The dev.drew.restaurantreview.util package contains utility classes and interfaces used throughout the application.

**Directory Structure:** All utility classes and interfaces should be placed in a util package within the application.

Class: **`SecurityUtils`**
- **Description:** This class provides utility methods related to security and authentication. The class contains three static methods, which are described below.
  - **getCurrentUserId():** This method returns the ID of the currently authenticated user. It retrieves the Authentication object from the SecurityContextHolder and extracts the UserEntity object associated with it. It then returns the ID of the UserEntity object.
  - **isAdmin():** This method returns a boolean value indicating whether the currently authenticated user has the "ROLE_ADMIN" role. It retrieves the Authentication object from the SecurityContextHolder and checks if the associated SecurityUser object has the "ROLE_ADMIN" role.
  - **isAdminOrOwner(entity, userIdProvider):** This method returns a boolean value indicating whether the currently authenticated user is an admin or the owner of a given entity. It takes two arguments: the entity to check ownership of, and a EntityUserIdProvider instance which is used to extract the user ID associated with the entity. The method first checks if the user is an admin by calling the isAdmin() method. If the user is not an admin, it retrieves the ID of the currently authenticated user and compares it to the ID of the user associated with the entity, which is extracted using the userIdProvider argument.

---

### Package: **`dev.drew.restaurantreview.util.interfaces`**
**Purpose:** This package contains interfaces used for providing functionality across the application. In this case, it includes the EntityUserIdProvider interface, which is used for obtaining the ID of an entity in the system.

**Directory Structure:** Interfaces should be placed in a util.interfaces package within the application.

Class: **`EntityUserIdProvider`**
- **Description:** This interface defines a method for obtaining the ID of an entity in the system. It is used as a functional interface for providing a way to get the user ID from an entity when needed. The class takes in a generic type **`T`**, which represents the entity type that it is used with. The method **`getUserId`** takes in an instance of the generic type **`T`** and returns a **`Long`** value representing the ID of the entity. This interface is implemented by classes that need to obtain the ID of an entity in order to perform operations on it.

---

## 6. Core Features
*Detail the main functionalities of the application and their corresponding code implementations. Describe the APIs, their endpoints, request and response models, and any specific business rules.*

### **6.1 User Management**

The application provides user management features through the **`UserService`** interface and its implementation in **`UserServiceImpl`**. These features include adding a new user, getting a user by their ID, deleting a user by their ID, and updating a user by their ID.

**Endpoints:**

- **POST /users**: This endpoint is used to add a new user. It takes a JSON payload containing user information, such as username, password, and email. The **`UserServiceImpl`** class handles the creation of the user in the database.
- **GET /users/{id}**: This endpoint retrieves a user by their ID. The **`UserServiceImpl`** class fetches the user from the database using the **`UserRepository`**.
- **PUT /users/{id}**: This endpoint updates a user by their ID. It takes a JSON payload containing the updated user information. The **`UserServiceImpl`** class is responsible for updating the user in the database.
- **DELETE /users/{id}**: This endpoint deletes a user by their ID. The **`UserServiceImpl`** class handles the deletion of the user from the database.

### **6.2 Restaurant Management**

The application provides restaurant management features through the **`RestaurantService`** interface and its implementation in **`RestaurantServiceImpl`**. These features include adding new restaurants, retrieving all restaurants with optional filters, getting a specific restaurant by ID, updating a restaurant by ID, and deleting a restaurant by ID.

**Endpoints:**

- **POST /restaurants**: This endpoint is used to add a new restaurant. It takes a JSON payload containing restaurant information, such as name, address, and description. The **`RestaurantServiceImpl`** class handles the creation of the restaurant in the database.
- **GET /restaurants**: This endpoint retrieves all restaurants, with optional filters like a search query or a specific location. The **`RestaurantServiceImpl`** class fetches the restaurants from the database using the **`RestaurantRepository`**.
- **GET /restaurants/{id}**: This endpoint retrieves a specific restaurant by its ID. The **`RestaurantServiceImpl`** class fetches the restaurant from the database using the **`RestaurantRepository`**.
- **PUT /restaurants/{id}**: This endpoint updates a restaurant by its ID. It takes a JSON payload containing the updated restaurant information. The **`RestaurantServiceImpl`** class is responsible for updating the restaurant in the database.
- **DELETE /restaurants/{id}**: This endpoint deletes a restaurant by its ID. The **`RestaurantServiceImpl`** class handles the deletion of the restaurant from the database.

### **6.3 Review Management**

The application provides review management features through the **`ReviewService`** interface and its implementation in **`ReviewServiceImpl`**. These features include adding new reviews, retrieving all reviews with optional filters, getting a specific review by ID, updating a review by ID, and deleting a review by ID.

**Endpoints:**

- **POST /reviews**: This endpoint is used to add a new review. It takes a JSON payload containing review information, such as the restaurant ID, user ID, rating, and comment. The **`ReviewServiceImpl`** class handles the creation of the review in the database.
- **GET /reviews**: This endpoint retrieves all reviews, with optional filters like a search query or a specific user or restaurant. The **`ReviewServiceImpl`** class fetches the reviews from the database using the **`ReviewRepository`**.
- **GET /reviews/{id}**: This endpoint retrieves a specific review by its ID. The **`ReviewServiceImpl`** class fetches the review from the database using the **`ReviewRepository`**.
- **PUT /reviews/{id}**: This endpoint updates a review by its ID. It takes a JSON payload containing the updated review information, such as rating and comment. The **`ReviewServiceImpl`** class is responsible for updating the review in the database.
- **DELETE /reviews/{id}**: This endpoint deletes a review by its ID. The **`ReviewServiceImpl`** class handles the deletion of the review from the database.

### **6.4 Authentication and Authorization**

The application provides authentication and authorization features using the **`JpaUserDetailsService`** class and the **`SecurityUtils`** utility class. Users are authenticated based on their username and password. User roles determine their authorization levels, with the "ADMIN" role having additional privileges.

**Endpoints:**

- **POST /auth/login**: This endpoint is used for user authentication. It takes a JSON payload containing the user's username and password. The **`JpaUserDetailsService`** class is responsible for loading user details and checking the provided credentials.
- **POST /auth/register**: This endpoint is used for user registration. It takes a JSON payload containing the user's username, password, and email. The **`UserServiceImpl`** class handles the creation of the new user in the database.

**Business Rules:**

- Users with the "ADMIN" role have additional privileges, such as deleting and updating any restaurant or review, regardless of ownership.
- Users without the "ADMIN" role can only delete or update their own reviews or restaurants.
- The **`SecurityUtils`** utility class provides methods like **`getCurrentUserId()`**, **`isAdmin()`**, and **`isAdminOrOwner()`** to facilitate checking user permissions and roles.

In summary, the Restaurant Review application offers core features related to user management, restaurant management, review management, and authentication/authorization. The code implementation is organized into controllers, services, repositories, and utility classes, with specific business rules enforced through the **`SecurityUtils`** utility class and user roles.

---

## 7. Error Handling
*Explain how the application handles errors and exceptions. Describe how error messages and HTTP status codes are returned.*

The application handles errors and exceptions at the service level, where each method in the service classes manages exceptions by catching them and returning appropriate HTTP status codes along with error messages. This approach ensures that the error handling is consistent across methods and provides meaningful error messages and status codes to clients.

For example, in the **`RestaurantServiceImpl`** class, the **`addNewRestaurant`** method handles various types of exceptions as follows:

1. **`DataIntegrityViolationException`**: This exception is thrown when there's a database constraint violation, such as unique constraints. The method responds with a **`HttpStatus.BAD_REQUEST`** status and a detailed error message.
2. **`DataAccessException`**: This exception is thrown for other database-related exceptions, and the method responds with a **`HttpStatus.INTERNAL_SERVER_ERROR`** status and a detailed error message.
3. **`Exception`**: This is a catch-all for any other exceptions that may occur. The method responds with a **`HttpStatus.INTERNAL_SERVER_ERROR`** status and a detailed error message.

The other methods in the service classes follow a similar approach to handling exceptions, ensuring that clients receive appropriate and meaningful responses when errors occur. Although the application does not utilize a global exception handler, the consistent handling of exceptions within service methods effectively addresses error handling requirements.

---

## 8. Testing
*Provide instructions on how to run tests for the application. Explain the testing strategy, including unit tests, integration tests, and any other testing approaches used.*

---

## 9. Deployment
*Outline the steps for deploying the application to a production environment. Mention any specific configurations or environment variables required.*

---

## 10. Troubleshooting and FAQs
*List common issues and their solutions. Include any frequently asked questions and their answers.*

---

## 11. Contributing
*Explain the process for submitting bug reports, feature requests, or contributing code. Include information on how to join the development community or get help if needed.*

---

## 12. License and Credits
*Mention the software license and give credit to any third-party libraries or resources used.*