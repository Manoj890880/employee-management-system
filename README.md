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

## 🧪 Testing

Run the tests with:

```bash
mvn test
```

The project includes comprehensive tests for:
- Controller layer
- Service layer

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
            └── service          # Service tests
```

## 🧪 Postman Collection

To help you test the API endpoints, we've included a Postman collection:

### Option 1: Import from File
1. Download the collection manually from the raw link:
   👉 [Right-click this link and choose "Save Link As"](https://github.com/Manoj890880/employee-management-system/raw/main/employee-apis/src/main/resources/docs/Employee%20Management%20API.postman_collection.json)
2. Open Postman
3. Click **"Import" > "File"**, and select the downloaded `.json` file

### Option 2: Import from GitHub directly
1. Open Postman
2. Click "Import" > "Link"
3. Paste this URL: `https://github.com/Manoj890880/employee-management-system/raw/main/employee-apis/src/main/resources/docs/Employee%20Management%20API.postman_collection.json`
4. Click "Import"

The collection includes ready-to-use requests for all endpoints with examples and environment variables.

## 📧 Contact

manojsahoo890880@gmail.com

Project Link: [https://github.com/Manoj890880/employee-management-system](https://github.com/Manoj890880/employee-management-system)
