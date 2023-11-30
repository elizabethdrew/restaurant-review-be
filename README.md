# Restaurant Review API

The Restaurant Review project is an API designed to allow users to discover, rate, and review restaurants. The main objective of this API is to provide a simple and efficient way to find the best dining experiences while helping restaurant owners gather valuable feedback to improve their services.

## Main Features

- User registration and authentication: Users can sign up for an account and securely log in to access the API's features.
- Browsing restaurants: Users can view a list of restaurants, filter them by various criteria such as city and rating.
- Restaurant details: Users can view detailed information about a specific restaurant, including its location and reviews.
- Rating and reviewing restaurants: Users can rate and write reviews for restaurants they've visited, providing valuable feedback for both restaurant owners and other users.
- Restaurant management: Restaurant owners or admins can add, update, and delete restaurant listings.

## Built With

- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- OpenAPI
- Liquibase

## Getting Started

### Prerequisites

You should have Java, Maven, and Docker installed on your machine. Follow the steps below to install them:

- [Java 17](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)

### **Installing Java and Maven**

### Using SDKMAN!

SDKMAN! is a tool for managing parallel versions of multiple Software Development Kits on Unix based systems. It provides a convenient Command Line Interface (CLI) and API for installing, switching, removing and listing Candidates.

To install SDKMAN!, open a new terminal and enter:

```
curl -s "https://get.sdkman.io" | bash
```

Then, open a new terminal or enter:

```
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Now, you can install Java and Maven using SDKMAN!:

```
sdk install java
sdk install maven
```

### **Manually**
### Installing Java

Download and install Java 17 from the [official website](https://www.oracle.com/java/technologies/downloads/).

### Installing Maven

### For Windows and Linux:

Download and install Maven from the [official website](https://maven.apache.org/download.cgi).

### For macOS:

If you have Homebrew installed, you can install Maven by running:

```
brew install maven

```

If you do not have Homebrew installed, you can install it by running:

```
/bin/bash -c "$(curl -fsSL <https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh>)"
brew install maven

```

### Installing Docker

Follow the instructions on the [official website](https://docs.docker.com/get-docker/) to install Docker and Docker Compose.

## Running the Application

1. First, clone the project repository. If you already have the project cloned, you can move onto the next step:

```
git clone https://gitlab.com/cfg-projects/restaurant-review-api
```

2. Pull the latest version of the projects

```
git pull https://gitlab.com/cfg-projects/restaurant-review-api main
```

3. Navigate to the project root directory.

4. Build the project with seed data:

```
mvn spring-boot:build-image -Dmaven.test.skip=true 
```

5. You can then run the Docker compose configuration:

```
docker-compose up --build -d
```

The application should now be up and running on [http://localhost:8080](http://localhost:8080/).

If it isn't running, check that all three parts of the docker restaurant-review-api are running. If they aren't, press the start button and try accessing it again.


## Authenticating

The database is seeded with an Admin user and Review User. Use the following credentials to authenticate:

Username: admin

Password: admin123

or 

Username: reviewer

Password: reviewer123

To authenticate, make a POST request to **http://localhost:8080/login** with the username and password. This will return a JWT token, which you will need to include in the Authorization header of your requests to interact with the rest of the API.

## API Documentation

The API documentation is provided using OpenAPI and can be explored using Swagger UI.

Once you have the application up and running locally, you can navigate to:

http://localhost:8080/swagger-ui/index.html

Here, you can explore the API's endpoints, understand the structure of request payloads, responses, and even interact with the API by sending requests directly from the interface.

Remember, the application must be running to access the Swagger UI and interact with the API.

## Postman

To play with the api using Postman you can use the links below.

V1 only endpoints: https://cfg-wcdio.postman.co/workspace/Restaurant-Review-API~ab4ce0d5-3f64-4d40-a6eb-7f[…]-environment=26377513-198c55f1-5fe6-473a-a7c8-bf6232abfd2b
Updated endpoints (in V1 mode): https://cfg-wcdio.postman.co/workspace/Restaurant-Review-API~ab4ce0d5-3f64-4d40-a6eb-7f[…]-environment=26377513-198c55f1-5fe6-473a-a7c8-bf6232abfd2b
Updated endpoints (in V2 mode): https://cfg-wcdio.postman.co/workspace/Restaurant-Review-API~ab4ce0d5-3f64-4d40-a6eb-7f[…]-environment=26377513-14e8ed8e-9f0d-40b1-ba62-2ccd446d49c5

You will need to get a JWT bearer token via the Get Authentication method to be able to use secured endpoints.