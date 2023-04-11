# Application Documentation

## 1. Introduction
*Briefly describe the purpose of the application and its main features. Mention any external dependencies or frameworks used.*

---

## 2. Prerequisites
*List the required software and tools to build, run, and test the application. Include version numbers, if necessary.*

---

## 3. Setup and Installation
*Provide step-by-step instructions for setting up the development environment. Describe how to install dependencies, configure the application, and run it locally.*

---

## 4. Application Architecture
*Give an overview of the main components and layers of the application. Include a simple diagram illustrating the architecture.*

---

## 5. Code Structure
*Briefly describe each package and its purpose. Explain the main classes and interfaces within each package. Provide guidance on naming conventions, directory structures, and other best practices followed in the codebase.*

**Naming Conventions:** Class names should be in `PascalCase`, describing their purpose. Method names should be in `camelCase` and start with a verb.

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
    - **`@EqualsAndHashCode(callSuper = true)`**: A Lombok annotation that indicates the generated **`equals`** and **`hashCode`** methods should call the superclass's methods.
- **Fields:**
    - **`id`**: The primary key for the restaurant table. It is autogenerated using the **`GenerationType.IDENTITY`** strategy.
    - **`name`**: The name of the restaurant. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`city`**: The city where the restaurant is located. It is also marked with **`@NotNull`**.
    - **`userId`**: The ID of the user who added the restaurant. This field is nullable.
    - **`rating`**: The average rating of the restaurant. It is marked with **`@NotNull`**, **`@Min(1)`** and **`@Max(5)`** to ensure that it must have a value between 1 and 5.
    - **`createdAt`**: The date and time when the restaurant was created, stored as an **`OffsetDateTime`**. It is annotated with **`@DateTimeFormat`** to specify the date-time format.

**Class: `ReviewEntity`**
- **Description:** This class represents a review in the application. It extends the **`org.openapitools.model.Review`** class and includes JPA annotations to map the class to the **`review`** table in the database.
    - **`@Entity`**: Specifies that the class is an entity and is mapped to a database table.
    - **`@Table(name = "review")`**: Specifies the name of the table to which this entity class is mapped.
    - **`@Data`**: A Lombok annotation that generates getters and setters for all fields, as well as **`equals`**, **`canEqual`**, **`hashCode`**, and **`toString`** implementations.
    - **`@NoArgsConstructor`**: A Lombok annotation that generates a no-argument constructor.
    - **`@AllArgsConstructor`**: A Lombok annotation that generates a constructor with all fields as arguments.
    - **`@EqualsAndHashCode(callSuper = true)`**: A Lombok annotation that indicates the generated **`equals`** and **`hashCode`** methods should call the superclass's methods.
- **Fields:**
    - **`id`**: The primary key for the review table. It is autogenerated using the **`GenerationType.IDENTITY`** strategy.
    - **`restaurantId`**: The ID of the restaurant being reviewed. It is marked with **`@NotNull`** to ensure that it must have a value.
    - **`userId`**: The ID of the user who wrote the review. This field is nullable.
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
    - **`@EqualsAndHashCode(callSuper = true)`**: A Lombok annotation that indicates the generated **`equals`** and **`hashCode`** methods should call the superclass's methods.
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
    - **`getAuthorities()`**: Returns a collection of granted authorities for the user, which represent their roles in the application. In this case, it returns a singleton list containing a **`SimpleGrantedAuthority`** object with the user's role.
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

---

## 7. Error Handling
*Explain how the application handles errors and exceptions. Describe how error messages and HTTP status codes are returned.*

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