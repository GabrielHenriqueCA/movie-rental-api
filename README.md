# Movie Rental API

Movie Rental API is a RESTful API built using Java and the Spring Boot framework. It allows users to rent movies from a movie library.

## Features

- User registration and authentication
- Browse available movies in the library
- Rent a movie
- View current rentals

## Getting Started

### Prerequisites

- Java
- MySQL

### Installation

1. Clone the repository:

```
git clone https://github.com/GabrielHenriqueCA/movie-rental-api.git
```

2. Create a MySQL database and update the database credentials in the `application.properties` file:

```
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=db_username
spring.datasource.password=db_password
```

3. Run the application:

```
./mvnw spring-boot:run Or launch the application from the IDE 
```

4. The API will be available at `http://localhost:8080`.

## API Documentation

API documentation can be found in the [docs](https://github.com/GabrielHenriqueCA/movie-rental-api/blob/main/docs) folder.

## Future Work

In the future, I plan to use this API in a front-end framework to create a full-stack movie rental application.

## Contributing

Contributions are welcome! Please see the [CONTRIBUTING](https://github.com/GabrielHenriqueCA/movie-rental-api/blob/main/CONTRIBUTING.md) file for more information.

## License

This project is licensed under the Apache License. See the [LICENSE](https://github.com/GabrielHenriqueCA/movie-rental-api/blob/main/LICENSE) file for more information.
