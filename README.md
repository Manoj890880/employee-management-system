# Employee Management API

A robust RESTful API for employee management built with Spring Boot that allows you to create, retrieve, update, and delete employee records with advanced filtering and pagination capabilities.

## 📋 Features

- **CRUD Operations**: Complete set of Create, Read, Update, and Delete operations for employee records
- **Advanced Filtering**: Filter employees by department, salary range, and joining date
- **Pagination & Sorting**: Built-in pagination and sorting capabilities
- **Validation**: Input validation for all API endpoints
- **Exception Handling**: Comprehensive exception handling with appropriate HTTP status codes

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA**: For data persistence and database operations
- **Hibernate Validator**: For input validation
- **ModelMapper**: For entity-DTO conversions
- **Lombok**: To reduce boilerplate code
- **H2/MySQL**: Database (configurable)
- **Maven**: Dependency management and build tool
- **JUnit 5 & Mockito**: For testing

## 🚀 Getting Started

### Prerequisites

- JDK 17 or later
- Maven 3.6 or later
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Installation & Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/employee-api.git
   cd employee-api
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## 📖 API Documentation

### Endpoints

#### Create a new employee
```
POST /employees
```
Request body:
```json
{
  "name": "John Doe",
  "department": "IT",
  "email": "john.doe@example.com",
  "salary": 75000.0,
  "joiningDate": "2023-01-15"
}
```

#### Get employee by ID
```
GET /employees/{id}
```

#### Update an employee
```
PUT /employees/{id}
```
Request body: Same as for creating an employee

#### Delete an employee
```
DELETE /employees/{id}
```

#### Get all employees with filtering, pagination and sorting
```
GET /employees
```
Query parameters:
- `department`: Filter by department (e.g., "IT", "HR")
- `minSalary`: Filter by minimum salary
- `maxSalary`: Filter by maximum salary
- `joinedAfter`: Filter by joining date after this date (format: yyyy-MM-dd)
- `joinedBefore`: Filter by joining date before this date (format: yyyy-MM-dd)
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)
- `sort`: Sorting parameters (default: "id,asc")

Example:
```
GET /employees?department=IT&minSalary=50000&joinedAfter=2023-01-01&page=0&size=10&sort=salary,desc
```

### Response Format

#### Single Employee
```json
{
  "id": 1,
  "name": "John Doe",
  "department": "IT",
  "email": "john.doe@example.com",
  "salary": 75000.0,
  "joiningDate": "2023-01-15"
}
```

#### Paginated Employees
```json
{
  "content": [
    {
      "id": 1,
      "name": "John Doe",
      "department": "IT",
      "email": "john.doe@example.com",
      "salary": 75000.0,
      "joiningDate": "2023-01-15"
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "department": "HR",
      "email": "jane.smith@example.com",
      "salary": 65000.0,
      "joiningDate": "2023-03-01"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 2,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

## ⚙️ Configuration

### Database Configuration

By default, the application uses an in-memory H2 database. To use a different database like MySQL, update the `application.properties` file:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Application Properties

The `application.properties` file in the `src/main/resources` directory contains various configuration options:

```properties
# Server Configuration
server.port=8080

# JPA/Hibernate Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Logging
logging.level.org.springframework=INFO
logging.level.com.brandsmashers=DEBUG

# Default date format
spring.jackson.date-format=yyyy-MM-dd
```

## 🧪 Testing

Run the tests with:

```bash
mvn test
```

The project includes comprehensive tests for:
- Controller layer
- Service layer
- Repository layer
- Integration tests

## 📝 Code Structure

```
src
├── main
│   ├── java
│   │   └── com.brandsmashers.employee_api
│   │       ├── controller       # REST controllers
│   │       ├── dto              # Data Transfer Objects
│   │       ├── exception        # Custom exceptions and global handler
│   │       ├── model            # Entity classes
│   │       ├── repository       # Data repositories
│   │       ├── service          # Business logic
│   │       └── specification    # Query specifications
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com.brandsmashers.employee_api
            ├── controller       # Controller tests
            ├── repository       # Repository tests
            └── service          # Service tests
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

Your Name - your.email@example.com

Project Link: [https://github.com/yourusername/employee-api](https://github.com/yourusername/employee-api)
