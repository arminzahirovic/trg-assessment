/**
 * The `HeartbeatScheduler` class is responsible for generating periodic heartbeats to update
 * the state of active cars in the fleet simulator.
 *
 * @Service Indicates that this class is a Spring service.
 */
package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartbeatScheduler {
    private final SimulatorService simulatorService;
    private final StateRepository stateRepository;

    @Autowired
    public HeartbeatScheduler(SimulatorService simulatorService, StateRepository stateRepository) {
        this.simulatorService = simulatorService;
        this.stateRepository = stateRepository;
    }

    /**
     * Generate heartbeats to update the state of active cars at fixed intervals.
     */
    @Scheduled(fixedRate = 10000) // 10 seconds interval
    public void generateHeartbeats() {
        // Retrieve all active states (where a driver is assigned).
        List<State> states = stateRepository.findAllActive();

        // Iterate through active states and simulate car movement.
        states.forEach(state -> {
            state = simulatorService.simulateCarMovement(state);
            stateRepository.save(state);
        });
    }
}