# Employees REST API

## Endpoints

### 1. Find Longest Working Pair

- **Endpoint:** `/api/employees/longest-working-pair`
- **Method:** `GET`
- **Response:** List of EmployeeDTO
- **Description:** Get the pair of employees who have worked together on common projects for the longest period of time and the duration for each of those projects.

### 2. Get Employee by ID

- **Endpoint:** `/api/employees/{id}`
- **Method:** `GET`
- **Response:** Employee details or 404 if not found
- **Parameters:**
  - `id`: Employee ID
- **Description:** Get employee details by their ID.

### 3. Create Employee

- **Endpoint:** `/api/employees`
- **Method:** `POST`
- **Request Body:** Employee details
- **Response:** Success message or error details
- **Description:** Create a new employee record.

### 4. Update Employee by ID

- **Endpoint:** `/api/employees/{id}`
- **Method:** `PUT`
- **Parameters:**
  - `id`: Employee ID
- **Request Body:** Updated employee details
- **Response:** Success message or error details
- **Description:** Update employee details by their ID.

### 5. Delete Employee by ID

- **Endpoint:** `/api/employees/{id}`
- **Method:** `DELETE`
- **Parameters:**
  - `id`: Employee ID
- **Response:** Success message or error details
- **Description:** Delete an employee record by their ID.
