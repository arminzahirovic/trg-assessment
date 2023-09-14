package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class HeartbeatSchedulerTest {

    @InjectMocks
    private HeartbeatScheduler heartbeatScheduler;

    @Mock
    private SimulatorService simulatorService;

    @Mock
    private StateRepository stateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateHeartbeats() {
        List<State> mockStates = new ArrayList<>();
        State state1 = new State();
        State state2 = new State();
        mockStates.add(state1);
        mockStates.add(state2);

        when(stateRepository.findAllActive()).thenReturn(mockStates);

        when(simulatorService.simulateCarMovement(any())).thenAnswer(invocation -> {
            State inputState = invocation.getArgument(0);
            return inputState;
        });

        heartbeatScheduler.generateHeartbeats();

        verify(stateRepository, times(2)).save(any(State.class));
    }
}
