# Library Management System

A JPA-based web application for managing a library, its documents (books and magazines), and users with borrowing functionality.

## Project Overview

This application provides a complete management system for a library with:

- Document management (Books and Magazines)
- User management
- Borrowing and returning functionality
- JSON-based RESTful API endpoints

## Technologies Used

- Java 23
- Jakarta EE 10
- JPA (EclipseLink)
- MySQL Database
- Gson for JSON serialization
- Maven for project management

## Project Structure

The application follows a layered architecture:

1. **Model Layer**: JPA entities (Document, Book, Magazine, User, Borrow)
2. **DAO Layer**: Interfaces and implementations for data access
3. **Servlet Layer**: RESTful API endpoints
4. **Utility**: Helper classes for database and JSON operations

## Setup & Installation

### Prerequisites

- Java 23 JDK
- MySQL Server
- Maven
- Jakarta EE compatible server (Tomcat, GlassFish, etc.)

### Database Configuration

The application is configured to connect to a MySQL database. Change the following properties in `src/main/resources/META-INF/persistence.xml` if needed:

```
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/exam01db?createDatabaseIfNotExist=true"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="0000"/>
```

### Running the Application

#### Option 1: Using the Test Application

For quick testing of functionality, run the `TestApp` class:

1. Ensure your MySQL server is running
2. Navigate to the project directory and run:

```
mvn clean compile exec:java -Dexec.mainClass="com.example.exam01.TestApp"
```

This will:
- Create sample entities (user, book)
- Create a borrowing relationship
- Display active borrows in the console

#### Option 2: Deploying to a Jakarta EE Server

1. Build the WAR file:

```
mvn clean package
```

2. Deploy the generated WAR file to your Jakarta EE server
3. Access the API endpoints at:
   - `/api/documents` - List all documents
   - `/api/books` - List all books or add a new book
   - `/api/magazines` - List all magazines or add a new magazine
   - `/api/users` - List all users or add a new user
   - `/api/borrows` - List active borrows or create a new borrow

## API Endpoints

### Documents API

- `GET /api/documents` - Returns all documents
- `GET /api/books` - Returns all books
- `POST /api/books` - Adds a new book (JSON body required)
- `GET /api/magazines` - Returns all magazines
- `POST /api/magazines` - Adds a new magazine (JSON body required)

### Users API

- `GET /api/users` - Returns all users
- `POST /api/users` - Registers a new user (JSON body required)

### Borrows API

- `GET /api/borrows` - Returns all active borrows
- `POST /api/borrows?userId={id}&documentId={id}` - Creates a new borrow

## Data Format

### Book Example

```
{
  "title": "Java Programming",
  "dateCreat": "2023-05-15",
  "author": "James Gosling",
  "isbn": "123-456-789",
  "datePublication": "2023-01-01"
}
```

### Magazine Example

```
{
  "title": "Tech Monthly",
  "dateCreat": "2023-05-15",
  "publisher": "Tech Media",
  "issueNumber": "42",
  "dateIssue": "2023-05-01"
}
```

### User Example

```
{
  "name": "John Doe",
  "mail": "john@example.com"
}
```

## Database Schema

The application uses a relational database with the following tables:

- `DOCUMENT` - Base table for all documents
- `BOOK` - Extends Document
- `MAGAZINE` - Extends Document
- `USERS` - Stores user information
- `BORROW` - Tracks borrowing relationships
- `BORROW_USER` - Junction table for many-to-many relationship
- `BORROW_DOCUMENT` - Junction table for many-to-many relationship
