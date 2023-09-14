/**
 * The `SimulatorService` class is responsible for simulating car movement by updating the state
 * information such as latitude, longitude, and speed.
 */
package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.entity.State;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimulatorService {
    private static final double LATITUDE_MIN = 40.426892;
    private static final double LATITUDE_MAX = 40.447862;
    private static final double LONGITUDE_MIN = -3.723850;
    private static final double LONGITUDE_MAX = -3.681278;
    private static final double SPEED_MIN = 20.0;
    private static final double SPEED_MAX = 120.0;
    private final Random random = new Random();

    /**
     * Simulate car movement by updating the state information.
     *
     * @param state The current state of the car.
     * @return The updated state after simulating car movement.
     */
    public State simulateCarMovement(State state) {
        // Generate new latitude, longitude, and speed values within defined ranges.
        double newLatitude = random.nextDouble() * (LATITUDE_MAX - LATITUDE_MIN) + LATITUDE_MIN;
        double newLongitude = random.nextDouble() * (LONGITUDE_MAX - LONGITUDE_MIN) + LONGITUDE_MIN;
        double newSpeed = random.nextDouble() * (SPEED_MAX - SPEED_MIN) + SPEED_MIN;

        // Update the state with the new values.
        state.setLatitude(newLatitude);
        state.setLongitude(newLongitude);
        state.setSpeed(newSpeed);

        return state;
    }
}
