# Fleet Management API Documentation

This API documentation provides information on the endpoints available in the Fleet Management application. The application consists of three main controllers: `StateController`, `CarController`, and `DriverController`. Below, you'll find details on each endpoint, including their usage, request parameters, and response formats.

---

## State Controller

**Base URL:** `http://localhost:8081`

### Get All Active States

- **Endpoint:** `/api/states`
- **HTTP Method:** `GET`
- **Description:** Retrieves a list of all active states.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of `StateResponse` objects representing active states.

### Assign Driver to Car

- **Endpoint:** `/api/states/cars/{carId}/assign-driver/{driverId}`
- **HTTP Method:** `POST`
- **Description:** Assigns a driver to a car.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to assign a driver to.
  - `driverId` (Path Parameter) - The ID of the driver to be assigned.
- **Response:**
  - Status Code: `200 OK`
  - Body: `State` object representing the updated state after the assignment.

### Unassign Driver from Car

- **Endpoint:** `/api/states/cars/{carId}/unassign-driver`
- **HTTP Method:** `POST`
- **Description:** Unassigns a driver from a car.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to unassign the driver from.
- **Response:**
  - Status Code: `200 OK`
  - Body: `State` object representing the updated state after the unassignment.

## Car Controller

**Base URL:** `http://localhost:8080`

### Create Car

- **Endpoint:** `/api/cars`
- **HTTP Method:** `POST`
- **Description:** Creates a new car.
- **Request Body:** JSON representation of the `Car` object.
- **Response:**
  - Status Code: `201 Created`
  - Body: `Car` object representing the created car.

### Get All Cars

- **Endpoint:** `/api/cars`
- **HTTP Method:** `GET`
- **Description:** Retrieves a list of all cars.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of `Car` objects representing cars.

### Get Car by ID

- **Endpoint:** `/api/cars/{carId}`
- **HTTP Method:** `GET`
- **Description:** Retrieves a specific car by its ID.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to retrieve.
- **Response:**
  - Status Code: `200 OK`
  - Body: `Car` object representing the requested car.
  - Status Code: `404 Not Found` if the car does not exist.

### Update Car

- **Endpoint:** `/api/cars`
- **HTTP Method:** `PUT`
- **Description:** Updates an existing car.
- **Request Body:** JSON representation of the updated `Car` object.
- **Response:**
  - Status Code: `201 Created`
  - Body: `Car` object representing the updated car.

### Delete Car

- **Endpoint:** `/api/cars/{carId}`
- **HTTP Method:** `DELETE`
- **Description:** Deletes a car by its ID.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to delete.
- **Response:**
  - Status Code: `204 No Content` if the car was successfully deleted.
  - Status Code: `404 Not Found` if the car does not exist.

### Assign Driver to Car

- **Endpoint:** `/api/cars/{carId}/assign-driver/{driverId}`
- **HTTP Method:** `POST`
- **Description:** Assigns a driver to a car.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to assign a driver to.
  - `driverId` (Path Parameter) - The ID of the driver to be assigned.
- **Response:**
  - Status Code: `200 OK`
  - Body: `State` object representing the updated state after the assignment.

### Unassign Driver from Car

- **Endpoint:** `/api/cars/{carId}/unassign-driver`
- **HTTP Method:** `POST`
- **Description:** Unassigns a driver from a car.
- **Request Parameters:**
  - `carId` (Path Parameter) - The ID of the car to unassign the driver from.
- **Response:**
  - Status Code: `200 OK`
  - Body: `State` object representing the updated state after the unassignment.

### Get Cars by IDs

- **Endpoint:** `/api/cars/filter-by-ids`
- **HTTP Method:** `POST`
- **Description:** Retrieves cars by a list of IDs.
- **Request Body:** List of car IDs.
- **Response:**
  - Status Code: `200 OK`
  - Body: Map of car IDs to `Car` objects representing the filtered cars.
  - Status Code: `404 Not Found` if no cars match the provided IDs.

## Driver Controller

### Save Driver

- **Endpoint:** `/api/drivers`
- **HTTP Method:** `POST`
- **Description:** Creates a new driver.
- **Request Body:** JSON representation of the `Driver` object.
- **Response:**
  - Status Code: `201 Created`
  - Body: `Driver` object representing the created driver.

### Get All Drivers

- **Endpoint:** `/api/drivers`
- **HTTP Method:** `GET`
- **Description:** Retrieves a list of all drivers.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of `Driver` objects representing drivers.

### Get Driver by ID

- **Endpoint:** `/api/drivers/{driverId}`
- **HTTP Method:** `GET`
- **Description:** Retrieves a specific driver by their ID.
- **Request Parameters:**
  - `driverId` (Path Parameter) - The ID of the driver to retrieve.
- **Response:**
  - Status Code: `200 OK`
  - Body: `Driver` object representing the requested driver.
  - Status Code: `404 Not Found` if the driver does not exist.

### Update Driver

- **Endpoint:** `/api/drivers`
- **HTTP Method:** `PUT`
- **Description:** Updates an existing driver.
- **Request Body:** JSON representation of the updated `Driver` object.
- **Response:**
  - Status Code: `201 Created`
  - Body: `Driver` object representing the updated driver.

### Delete Driver

- **Endpoint:** `/api/drivers/{driverId}`
- **HTTP Method:** `DELETE`
- **Description:** Deletes a driver by their ID.
- **Request Parameters:**
  - `driverId` (Path Parameter) - The ID of the driver to delete.
- **Response:**
  - Status Code: `204 No Content` if the driver was successfully deleted.
  - Status Code: `404 Not Found` if the driver does not exist.

### Get Drivers by IDs

- **Endpoint:** `/api/drivers/filter-by-ids`
- **HTTP Method:** `POST`
- **Description:** Retrieves drivers by a list of IDs.
- **Request Body:** List of driver IDs.
- **Response:**
  - Status Code: `200 OK`
  - Body: Map of driver IDs to `Driver` objects representing the filtered drivers.
  - Status Code: `404 Not Found` if no drivers match the provided IDs.

### Get Available Drivers

- **Endpoint:** `/api/drivers/available`
- **HTTP Method:** `GET`
- **Description:** Retrieves a list of available drivers.
- **Request Parameters:**
  - `includedCarId` (Query Parameter, optional) - The ID of the car to include in the search for available drivers.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of `Driver` objects representing available drivers.
