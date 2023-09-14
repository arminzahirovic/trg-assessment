/**
 * The `CarController` class is a RESTful controller responsible for handling HTTP requests
 * related to car operations. It provides endpoints for creating, retrieving, updating,
 * and deleting cars, as well as assigning and unassigning drivers to/from cars and
 * filtering cars by their IDs.
 *
 * @RestController Indicates that this class is a controller that handles HTTP requests.
 * @RequestMapping("/api/cars") Specifies the base URL for all endpoints in this controller.
 * @CrossOrigin(origins = "http://localhost:4200") Allows cross-origin requests from
 *       http://localhost:4200 to access these endpoints.
 */
package com.trgassessment.fleetservice.controller;

import com.trgassessment.fleetservice.DTO.State;
import com.trgassessment.fleetservice.entity.Car;
import com.trgassessment.fleetservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cars")
@CrossOrigin(origins = "http://localhost:4200")
public class CarController {

    private CarService carService;

    /**
     * Constructor for the `CarController` class.
     *
     * @param carService An instance of the `CarService` class that provides car-related services.
     */
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Create a new car.
     *
     * @param car The car object to be created.
     * @return A ResponseEntity with HTTP status 201 (Created) and the created car object.
     */
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.createCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    /**
     * Get a list of all cars.
     *
     * @return A ResponseEntity with HTTP status 200 (OK) and a list of all cars.
     */
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    /**
     * Get a car by its ID.
     *
     * @param carId The ID of the car to retrieve.
     * @return A ResponseEntity with HTTP status 200 (OK) and the car object if found,
     *         or HTTP status 404 (Not Found) if the car does not exist.
     */
    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        Car car = carService.getCarById(carId);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update an existing car.
     *
     * @param updatedCar The updated car object.
     * @return A ResponseEntity with HTTP status 201 (Created) and the updated car object.
     */
    @PutMapping
    public ResponseEntity<Car> updateCar(@RequestBody Car updatedCar) {
        Car savedCar = carService.updateCar(updatedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    /**
     * Delete a car by its ID.
     *
     * @param carId The ID of the car to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) after successful deletion.
     */
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Assign a driver to a car.
     *
     * @param carId    The ID of the car to which the driver will be assigned.
     * @param driverId The ID of the driver to be assigned to the car.
     * @return A ResponseEntity with HTTP status 200 (OK) and a state object indicating success
     *         or an exception if the assignment fails.
     * @throws NotFoundException If the car or driver is not found.
     */
    @PostMapping("/{carId}/assign-driver/{driverId}")
    public ResponseEntity<State> assignDriverToCar(
            @PathVariable Long carId,
            @PathVariable Long driverId
    ) throws NotFoundException {
        State state = carService.assignDriverToCar(carId, driverId);
        return ResponseEntity.ok(state);
    }

    /**
     * Unassign a driver from a car.
     *
     * @param carId The ID of the car from which the driver will be unassigned.
     * @return A ResponseEntity with HTTP status 200 (OK) and a state object indicating success
     *         or an exception if the unassignment fails.
     * @throws NotFoundException If the car or driver is not found.
     */
    @PostMapping("/{carId}/unassign-driver")
    public ResponseEntity<State> unassignDriverFromCar(
            @PathVariable Long carId
    ) throws NotFoundException {
        State state = carService.unassignDriverFromCar(carId);
        return ResponseEntity.ok(state);
    }

    /**
     * Filter cars by a list of IDs.
     *
     * @param carsIds A list of car IDs to filter by.
     * @return A ResponseEntity with HTTP status 200 (OK) and a map of car IDs to car objects
     *         if any cars are found, or HTTP status 404 (Not Found) if none are found.
     */
    @PostMapping("/filter-by-ids")
    public ResponseEntity<Map<Long, Car>> getCarsByIds(@RequestBody List<Long> carsIds) {
        Map<Long, Car> cars = carService.getCarsByListOfIds(carsIds);
        if (cars != null) {
            return ResponseEntity.ok(cars);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
