# Employee Management System - REST API

## Table of Contents

- [Endpoints](#endpoints)

## Endpoints

### Get pair of employees who have worked together on common projects 
### for the longest period of time and the time for each of those projects.

- **Endpoint:** `/api/employees/longest-working-pair`
- **Method:** `GET`
- **Response:** List of EmployeeDTO
- **Parameters:** None

### Get Employee by ID

- **Endpoint:** `/api/employees/{id}`
- **Method:** `GET`
- **Response:** Employee Details
- **Parameters:** `id` (Long)

### Create Employee

- **Endpoint:** `/api/employees`
- **Method:** `POST`
- **Response:** Success message
- **Parameters:** Employee data in the request body

### Update Employee

- **Endpoint:** `/api/employees/{id}`
- **Method:** `PUT`
- **Response:** Success message
- **Parameters:** `id` (Long), Updated Employee data in the request body

### Delete Employee

- **Endpoint:** `/api/employees/{id}`
- **Method:** `DELETE`
- **Response:** Success message
- **Parameters:** `id` (Long)
