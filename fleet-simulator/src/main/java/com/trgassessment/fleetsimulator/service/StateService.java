/**
 * The `StateService` class is responsible for managing car state information and interactions
 * with external services in the fleet simulator.
 *
 * @Service Indicates that this class is a Spring service.
 */
package com.trgassessment.fleetsimulator.service;

import com.trgassessment.fleetsimulator.DTO.Car;
import com.trgassessment.fleetsimulator.entity.State;
import com.trgassessment.fleetsimulator.DTO.StateResponse;
import com.trgassessment.fleetsimulator.DTO.Driver;
import com.trgassessment.fleetsimulator.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepository stateRepository;
    private final WebClient.Builder webClientBuilder;
    private HttpHeaders headers;

    @Autowired
    public StateService(StateRepository stateRepository, WebClient.Builder webClientBuilder) {
        this.stateRepository = stateRepository;
        this.webClientBuilder = webClientBuilder;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Assign a driver to a car and update the car's state.
     *
     * @param carId    The ID of the car to which the driver will be assigned.
     * @param driverId The ID of the driver to be assigned to the car.
     * @return The updated state of the car after assigning the driver.
     */
    public State assignDriverToCar(Long carId, Long driverId) {
        State state = stateRepository.findById(carId).orElse(null);

        if (state == null) {
            state = new State();
            state.setSpeed(10.00);
            state.setLatitude(40.00);
            state.setLongitude(40.00);
        }

        state.setCarId(carId);
        state.setDriverId(driverId);
        stateRepository.save(state);

        return state;
    }

    /**
     * Unassign a driver from a car and update the car's state.
     *
     * @param carId The ID of the car from which the driver will be unassigned.
     * @return The updated state of the car after unassigning the driver.
     */
    public State unassignDriverFromCar(Long carId) {
        State state = stateRepository.findById(carId).orElse(null);

        if (state != null) {
            state.setDriverId(null);
            state.setSpeed(0.0);
            stateRepository.save(state);
        }

        return state;
    }

    /**
     * Get a list of all active states with car, driver, and state information.
     *
     * @return A list of active car states with associated car and driver information.
     * @throws URISyntaxException If there is an issue with URI syntax.
     */
    public List<StateResponse> getAllActiveStates() throws URISyntaxException {
        List<State> states = stateRepository.findAllActive();

        List<Long> carsIds = states.stream()
                .map(state -> state.getCarId())
                .collect(Collectors.toList());
        Map<Long, Car> cars = fetchCarsData(carsIds);

        List<Long> driversIds = states.stream()
                .map(state -> state.getDriverId())
                .collect(Collectors.toList());
        Map<Long, Driver> drivers = fetchDriversData(driversIds);

        return states.stream()
                .map(state -> {
                    StateResponse stateResponse = new StateResponse();
                    stateResponse.setCar(cars.get(state.getCarId()));
                    stateResponse.setDriver(drivers.get(state.getDriverId()));
                    stateResponse.setSpeed(state.getSpeed());
                    stateResponse.setLongitude(state.getLongitude());
                    stateResponse.setLatitude(state.getLatitude());
                    return stateResponse;
                })
                .collect(Collectors.toList());
    }

    /**
     * Fetches car data from an external API using WebClient and returns a map of car IDs to Car objects.
     *
     * @param carsIds A list of car IDs to fetch data for.
     * @return A map where keys are car IDs and values are corresponding Car objects.
     * @throws URISyntaxException If there is an issue with URI syntax.
     */
    private Map<Long, Car> fetchCarsData(List<Long> carsIds) throws URISyntaxException {
        WebClient webClient = webClientBuilder.baseUrl("http://fleet-service:8080/api/").build();

        return webClient.post()
                .uri("cars/filter-by-ids")
                .body(Mono.just(carsIds), List.class)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<Long, Car>>(){})
                .blockFirst();
    }

    /**
     * Fetches driver data from an external API using WebClient and returns a map of driver IDs to Driver objects.
     *
     * @param driversIds A list of driver IDs to fetch data for.
     * @return A map where keys are driver IDs and values are corresponding Driver objects.
     * @throws URISyntaxException If there is an issue with URI syntax.
     */
    private Map<Long, Driver> fetchDriversData(List<Long> driversIds) throws URISyntaxException {
        WebClient webClient = webClientBuilder.baseUrl("http://fleet-service:8080/api/").build();

        return webClient.post()
                .uri("drivers/filter-by-ids")
                .body(Mono.just(driversIds), List.class)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<Long, Driver>>(){})
                .blockFirst();
    }
}
