/**
 * The `DriverController` class is a RESTful controller responsible for handling HTTP requests
 * related to driver operations. It provides endpoints for creating, retrieving, updating,
 * and deleting drivers, as well as filtering drivers by their IDs and retrieving available drivers.
 *
 * @RestController Indicates that this class is a controller that handles HTTP requests.
 * @RequestMapping("/api/drivers") Specifies the base URL for all endpoints in this controller.
 * @CrossOrigin(origins = "http://localhost:4200") Allows cross-origin requests from
 *       http://localhost:4200 to access these endpoints.
 */
package com.trgassessment.fleetservice.controller;

import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "http://localhost:4200")
public class DriverController {

    private DriverService driverService;

    /**
     * Constructor for the `DriverController` class.
     *
     * @param driverService An instance of the `DriverService` class that provides driver-related services.
     */
    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Create a new driver.
     *
     * @param driver The driver object to be created.
     * @return A ResponseEntity with HTTP status 201 (Created) and the created driver object.
     */
    @PostMapping
    public ResponseEntity<Driver> saveDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverService.saveDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
    }

    /**
     * Get a list of all drivers.
     *
     * @return A ResponseEntity with HTTP status 200 (OK) and a list of all drivers.
     */
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    /**
     * Get a driver by their ID.
     *
     * @param driverId The ID of the driver to retrieve.
     * @return A ResponseEntity with HTTP status 200 (OK) and the driver object if found,
     *         or HTTP status 404 (Not Found) if the driver does not exist.
     */
    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long driverId) {
        Driver driver = driverService.getDriverById(driverId);
        if (driver != null) {
            return ResponseEntity.ok(driver);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update an existing driver.
     *
     * @param updatedDriver The updated driver object.
     * @return A ResponseEntity with HTTP status 201 (Created) and the updated driver object.
     */
    @PutMapping()
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver updatedDriver) {
        Driver savedDriver = driverService.saveDriver(updatedDriver);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
    }

    /**
     * Delete a driver by their ID.
     *
     * @param driverId The ID of the driver to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) after successful deletion.
     */
    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long driverId) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Filter drivers by a list of IDs.
     *
     * @param driversIds A list of driver IDs to filter by.
     * @return A ResponseEntity with HTTP status 200 (OK) and a map of driver IDs to driver objects
     *         if any drivers are found, or HTTP status 404 (Not Found) if none are found.
     */
    @PostMapping("/filter-by-ids")
    public ResponseEntity<Map<Long, Driver>> getDriversByIds(@RequestBody List<Long> driversIds) {
        Map<Long, Driver> drivers = driverService.getDriversByListOfIds(driversIds);
        if (drivers != null) {
            return ResponseEntity.ok(drivers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of available drivers.
     *
     * @param includedCarId The ID of the car to include in the availability check (optional).
     * @return A ResponseEntity with HTTP status 200 (OK) and a list of available drivers.
     */
    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers(@RequestParam(required = false) Long includedCarId) {
        List<Driver> drivers = driverService.getAvailableDrivers(includedCarId);
        return ResponseEntity.ok(drivers);
    }
}
