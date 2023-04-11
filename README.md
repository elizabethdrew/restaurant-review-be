# Application Documentation

## 1. Introduction
*Briefly describe the purpose of the application and its main features. Mention any external dependencies or frameworks used.*

## 2. Prerequisites
*List the required software and tools to build, run, and test the application. Include version numbers, if necessary.*

## 3. Setup and Installation
*Provide step-by-step instructions for setting up the development environment. Describe how to install dependencies, configure the application, and run it locally.*

## 4. Application Architecture
*Give an overview of the main components and layers of the application. Include a simple diagram illustrating the architecture.*

## 5. Code Structure

*Briefly describe each package and its purpose. Explain the main classes and interfaces within each package. Provide guidance on naming conventions, directory structures, and other best practices followed in the codebase.*

**Naming Conventions:** Class names should be in `PascalCase`, describing their purpose. Method names should be in `camelCase` and start with a verb.

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

**Interface: `RestaurantMapper`**

- **Description:** This interface defines the methods for mapping between the **`RestaurantEntity`**, **`Restaurant`**, and **`RestaurantInput`** classes. It is an interface annotated with **`@Mapper(componentModel = "spring")`**, which indicates that it is a MapStruct mapper with Spring integration.

---

### **Package: `dev.drew.restaurantreview.service`**
**Purpose:** This package contains the service layer classes responsible for the business logic of the application, such as interacting with repositories, mapping between model objects, and handling exceptions.

**Directory Structure:** Service classes should be placed in a **`service`** package within the application.

**Class: `RestaurantServiceImpl`**

- **Description:** This class implements the **`RestaurantService`** interface and is responsible for handling business logic related to restaurants. It interacts with the **`RestaurantRepository`** and **`ReviewRepository`**, as well as the **`RestaurantMapper`**. The class is annotated with **`@Service`**, which marks it as a Spring service bean.

## 6. Core Features
*Detail the main functionalities of the application and their corresponding code implementations. Describe the APIs, their endpoints, request and response models, and any specific business rules.*

## 7. Error Handling
*Explain how the application handles errors and exceptions. Describe how error messages and HTTP status codes are returned.*

## 8. Testing
*Provide instructions on how to run tests for the application. Explain the testing strategy, including unit tests, integration tests, and any other testing approaches used.*

## 9. Deployment
*Outline the steps for deploying the application to a production environment. Mention any specific configurations or environment variables required.*

## 10. Troubleshooting and FAQs
*List common issues and their solutions. Include any frequently asked questions and their answers.*

## 11. Contributing
*Explain the process for submitting bug reports, feature requests, or contributing code. Include information on how to join the development community or get help if needed.*

## 12. License and Credits
*Mention the software license and give credit to any third-party libraries or resources used.*