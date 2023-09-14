/**
 * The `DriverService` class provides business logic for driver-related operations.
 * It interacts with the `DriverRepository` to perform CRUD (Create, Read, Update, Delete)
 * operations on drivers and includes methods for retrieving all drivers, available drivers,
 * and drivers by a list of IDs.
 */
package com.trgassessment.fleetservice.service;

import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Get a list of all drivers.
     *
     * @return A list of all drivers in the database.
     */
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    /**
     * Get a list of available drivers.
     *
     * @param includedCarId The ID of the car to include in the availability check (optional).
     * @return A list of available drivers. If `includedCarId` is provided, drivers associated
     *         with that car are also included.
     */
    public List<Driver> getAvailableDrivers(Long includedCarId) {
        if (includedCarId != null) {
            return driverRepository.findAvailableDrivers(includedCarId);
        }

        return driverRepository.findAvailableDrivers();
    }

    /**
     * Save a new driver or update an existing one.
     *
     * @param driver The driver object to be saved or updated.
     * @return The saved or updated driver.
     */
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    /**
     * Delete a driver by its ID.
     *
     * @param driverId The ID of the driver to delete.
     */
    public void deleteDriver(Long driverId) {
        driverRepository.deleteById(driverId);
    }

    /**
     * Get a driver by its ID.
     *
     * @param driverId The ID of the driver to retrieve.
     * @return The driver if found, otherwise null.
     */
    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId).orElse(null);
    }

    /**
     * Get drivers by a list of IDs.
     *
     * @param driversIds A list of driver IDs to filter by.
     * @return A map of driver IDs to driver objects.
     */
    public Map<Long, Driver> getDriversByListOfIds(List<Long> driversIds) {
        List<Driver> drivers = driverRepository.findAllByIds(driversIds);
        return drivers.stream().collect(Collectors.toMap(Driver::getId, Function.identity()));
    }
}
