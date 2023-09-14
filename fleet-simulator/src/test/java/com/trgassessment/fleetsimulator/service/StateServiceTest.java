package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

class StateServiceTest {

    @Autowired
    private StateService stateService;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stateService = new StateService(stateRepository, webClientBuilder);
    }

    @Test
    void assignDriverToCar() {
        Long carId = 1L;
        Long driverId = 2L;
        State state = new State();
        state.setCarId(carId);
        when(stateRepository.findById(carId)).thenReturn(java.util.Optional.ofNullable(state));

        State result = stateService.assignDriverToCar(carId, driverId);

        assertEquals(driverId, result.getDriverId());
        verify(stateRepository, times(1)).findById(carId);
        verify(stateRepository, times(1)).save(state);
    }

    @Test
    void unassignDriverFromCar() throws Exception {
        Long carId = 1L;
        State state = new State();
        state.setCarId(carId);
        state.setDriverId(2L);
        when(stateRepository.findById(carId)).thenReturn(java.util.Optional.ofNullable(state));

        State result = stateService.unassignDriverFromCar(carId);

        assertNull(result.getDriverId());
        assertEquals(0.0, result.getSpeed());
        verify(stateRepository, times(1)).findById(carId);
        verify(stateRepository, times(1)).save(state);
    }
}