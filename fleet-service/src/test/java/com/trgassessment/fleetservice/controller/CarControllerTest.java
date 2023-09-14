package com.trgassessment.fleetservice.controller;

import com.trgassessment.fleetservice.DTO.State;
import com.trgassessment.fleetservice.entity.Car;
import com.trgassessment.fleetservice.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(carService.createCar(car)).thenReturn(car);

        ResponseEntity<Car> response = carController.createCar(car);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(car, response.getBody());
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);

        ResponseEntity<List<Car>> response = carController.getAllCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cars, response.getBody());
    }

    @Test
    void testGetCarById() {
        Long carId = 1L;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        ResponseEntity<Car> response = carController.getCarById(carId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(car, response.getBody());
    }

    @Test
    void testGetCarByIdNotFound() {
        Long carId = 1L;
        when(carService.getCarById(carId)).thenReturn(null);

        ResponseEntity<Car> response = carController.getCarById(carId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateCar() {
        Car updatedCar = new Car();
        when(carService.updateCar(updatedCar)).thenReturn(updatedCar);

        ResponseEntity<Car> response = carController.updateCar(updatedCar);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedCar, response.getBody());
    }

    @Test
    void testDeleteCar() {
        Long carId = 1L;
        ResponseEntity<Void> response = carController.deleteCar(carId);

        verify(carService, times(1)).deleteCar(carId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testAssignDriverToCar() throws ChangeSetPersister.NotFoundException {
        Long carId = 1L;
        Long driverId = 2L;
        when(carService.assignDriverToCar(carId, driverId)).thenReturn(new State());

        ResponseEntity<State> response = carController.assignDriverToCar(carId, driverId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUnassignDriverFromCar() throws ChangeSetPersister.NotFoundException {
        Long carId = 1L;
        when(carService.unassignDriverFromCar(carId)).thenReturn(new State());

        ResponseEntity<State> response = carController.unassignDriverFromCar(carId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCarsByIds() {
        List<Long> carIds = Arrays.asList(1L, 2L);
        Map<Long, Car> carMap = Map.of(1L, new Car(), 2L, new Car());
        when(carService.getCarsByListOfIds(carIds)).thenReturn(carMap);

        ResponseEntity<Map<Long, Car>> response = carController.getCarsByIds(carIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carMap, response.getBody());
    }

    @Test
    void testGetCarsByIdsNotFound() {
        List<Long> carIds = Arrays.asList(1L, 2L);
        when(carService.getCarsByListOfIds(carIds)).thenReturn(null);

        ResponseEntity<Map<Long, Car>> response = carController.getCarsByIds(carIds);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

