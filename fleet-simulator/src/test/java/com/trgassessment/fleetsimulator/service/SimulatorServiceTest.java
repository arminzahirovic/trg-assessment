package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.entity.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulatorServiceTest {

    private SimulatorService simulatorService;

    @BeforeEach
    public void setUp() {
        simulatorService = new SimulatorService();
    }

    @Test
    public void testSimulateCarMovement() {
        State state = new State(1L, 40.00, -70.00, 50.00, 2L);
        double initialLatitude = state.getLatitude();
        double initialLongitude = state.getLongitude();
        double initialSpeed = state.getSpeed();

        State simulatedState = simulatorService.simulateCarMovement(state);

        assertNotEquals(initialLatitude, simulatedState.getLatitude());
        assertNotEquals(initialLongitude, simulatedState.getLongitude());
        assertNotEquals(initialSpeed, simulatedState.getSpeed());
    }
}