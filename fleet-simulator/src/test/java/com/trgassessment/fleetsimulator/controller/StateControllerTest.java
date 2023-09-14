package com.trgassessment.fleetsimulator.controller;

import com.trgassessment.fleetsimulator.DTO.StateResponse;
import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateControllerTest {

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllActiveStates() throws URISyntaxException {
        List<StateResponse> states = new ArrayList<>();
        states.add(new StateResponse());
        states.add(new StateResponse());

        when(stateService.getAllActiveStates()).thenReturn(states);

        ResponseEntity<List<StateResponse>> response = stateController.getAllActiveStates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(states, response.getBody());
    }

    @Test
    public void testAssignDriverToCar() {
        Long carId = 1L;
        Long driverId = 2L;
        State state = new State();

        when(stateService.assignDriverToCar(carId, driverId)).thenReturn(state);

        ResponseEntity<State> response = stateController.assignDriverToCar(carId, driverId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(state, response.getBody());
    }

    @Test
    public void testAssignDriverToCarException() {
        Long carId = 1L;
        Long driverId = 2L;

        when(stateService.assignDriverToCar(carId, driverId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> {
            stateController.assignDriverToCar(carId, driverId);
        });
    }

    @Test
    public void testUnassignDriverFromCar() throws NotFoundException {
        Long carId = 1L;
        State state = new State();

        when(stateService.unassignDriverFromCar(carId)).thenReturn(state);

        ResponseEntity<State> response = stateController.unassignDriverFromCar(carId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(state, response.getBody());
    }
}