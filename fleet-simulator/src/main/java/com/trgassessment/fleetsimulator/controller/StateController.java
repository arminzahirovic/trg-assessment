/**
 * The `StateController` class is responsible for handling HTTP requests related to car state
 * management in the fleet simulator. It interacts with the `StateService` to perform operations
 * such as assigning and unassigning drivers to/from cars and retrieving active car states.
 */
package com.trgassessment.fleetsimulator.controller;

import com.trgassessment.fleetsimulator.DTO.StateResponse;
import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/states")
@CrossOrigin(origins = "http://localhost:4200")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Get a list of all active car states.
     *
     * @return A list of active car states.
     * @throws URISyntaxException If there is an issue with URI syntax.
     */
    @GetMapping
    public ResponseEntity<List<StateResponse>> getAllActiveStates() throws URISyntaxException {
        List<StateResponse> cars = stateService.getAllActiveStates();
        return ResponseEntity.ok(cars);
    }

    /**
     * Assign a driver to a car.
     *
     * @param carId    The ID of the car to which the driver will be assigned.
     * @param driverId The ID of the driver to be assigned to the car.
     * @return The state after assigning the driver.
     */
    @PostMapping("/cars/{carId}/assign-driver/{driverId}")
    public ResponseEntity<State> assignDriverToCar(@PathVariable Long carId, @PathVariable Long driverId) {
        State state = stateService.assignDriverToCar(carId, driverId);
        return ResponseEntity.ok(state);
    }

    /**
     * Unassign a driver from a car.
     *
     * @param carId The ID of the car from which the driver will be unassigned.
     * @return The state after unassigning the driver.
     */
    @PostMapping("/cars/{carId}/unassign-driver")
    public ResponseEntity<State> unassignDriverFromCar(@PathVariable Long carId) {
        State state = stateService.unassignDriverFromCar(carId);
        return ResponseEntity.ok(state);
    }
}
