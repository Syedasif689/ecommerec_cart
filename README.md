# E-Commerce Cart API

A backend REST API for managing products and shopping carts, developed using **Java, Spring Boot, Spring Data JPA, and MySQL**.

This project implements core e-commerce cart functionality such as adding products, updating quantities, removing cart items, calculating subtotals and totals, and validating product stock.

## Internship Details

**Intern ID:** CITS9357
**Task:** Task 1 — E-Commerce Cart Logic

## Features

* Product listing and product lookup
* Create a cart automatically when a customer adds a product
* Add products to cart
* Increase quantity when the same product is added again
* Update cart item quantity
* Remove cart items
* Calculate item subtotal
* Calculate cart total
* Product stock validation
* Product-not-found handling
* Cart-not-found handling
* Cart-item-not-found handling
* Request validation
* Centralized exception handling
* MySQL database persistence

## Technology Stack

* **Java 25**
* **Spring Boot 3.5.6**
* **Spring Web**
* **Spring Data JPA / Hibernate**
* **MySQL 8**
* **Maven**
* **Jakarta Validation**
* **Postman** for API testing

## Architecture

The application follows a layered architecture:

```text
Client / Postman
       ↓
Controller Layer
       ↓
Service Layer
       ↓
Repository Layer
       ↓
MySQL Database
```

### Main Packages

```text
com.codtech.ecommerce
│
├── controller
├── service
├── repository
├── entity
├── dto
└── exception
```

## Database Structure

The application uses three main tables:

### Products

Stores product information.

```text
id
name
price
stock
```

### Carts

Stores customer carts.

```text
id
customer_id
```

### Cart Items

Connects products with carts and stores the selected quantity.

```text
id
cart_id
product_id
quantity
```

Relationship:

```text
Product 1 ─────── * CartItem * ─────── 1 Cart
```

## API Endpoints

### Products

#### Get All Products

```http
GET /api/products
```

#### Get Product by ID

```http
GET /api/products/{productId}
```

Example:

```http
GET /api/products/1
```

---

### Cart

#### Add Product to Cart

```http
POST /api/cart/add
```

Request:

```json
{
    "customerId": "customer001",
    "productId": 1,
    "quantity": 2
}
```

#### Get Customer Cart

```http
GET /api/cart/{customerId}
```

Example:

```http
GET /api/cart/customer001
```

#### Update Cart Item

```http
PUT /api/cart/items/{cartItemId}
```

Request:

```json
{
    "quantity": 4
}
```

#### Remove Cart Item

```http
DELETE /api/cart/items/{cartItemId}
```

Example:

```http
DELETE /api/cart/items/1
```

## Example Cart Response

```json
{
    "cartId": 1,
    "customerId": "customer001",
    "items": [
        {
            "productId": 1,
            "productName": "Laptop",
            "price": 50000.00,
            "quantity": 2,
            "subtotal": 100000.00
        }
    ],
    "total": 100000.00
}
```

## Validation and Error Handling

The application validates incoming requests and handles common errors using a centralized exception handler.

Examples include:

```text
Product not found
Cart not found
Cart item not found
Insufficient stock
Invalid quantity
Missing required fields
```

Example stock validation:

```text
Requested quantity: 11
Available quantity: 10

Result: 400 Bad Request
```

## Sample Products

The project includes sample products through `data.sql`:

| Product        |   Price | Stock |
| -------------- | ------: | ----: |
| Laptop         | ₹50,000 |    10 |
| Wireless Mouse |  ₹1,000 |    25 |
| Keyboard       |  ₹1,500 |    15 |
| Headphones     |  ₹2,500 |    20 |
| Monitor        | ₹12,000 |     8 |

## Local Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Syedasif689/ecommerec_cart.git
cd ecommerec_cart
```

### 2. Create the MySQL Database

Create a database named:

```sql
CREATE DATABASE ecommerce_cart;
```

### 3. Configure MySQL

Create your local:

```text
src/main/resources/application.properties
```

Use the example configuration provided in:

```text
src/main/resources/application-example.properties
```

Set your own MySQL username and password.

**Do not commit your real database password to GitHub.**

### 4. Run the Application

Using the Maven wrapper:

```powershell
.\mvnw spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## Testing

The APIs were tested using **Postman**.

Tested functionality includes:

* Product retrieval
* Product lookup
* Adding products to cart
* Increasing existing product quantity
* Stock-limit validation
* Updating cart quantity
* Removing cart items
* Product-not-found handling
* Cart-not-found handling
* Missing request-field validation
* Invalid quantity validation

## Project Purpose

The purpose of this project is to implement the core backend logic of an e-commerce shopping cart using a layered Spring Boot architecture and persistent MySQL storage.

It demonstrates REST API development, database integration, entity relationships, business logic, validation, exception handling, and API testing.

## Author

**Syed Asif**

**Intern ID:** CITS9357

GitHub: `Syedasif689`
