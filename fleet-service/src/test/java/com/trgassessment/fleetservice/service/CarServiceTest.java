package com.trgassessment.fleetservice.service;

import com.trgassessment.fleetservice.entity.Car;
import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.repository.CarRepository;
import com.trgassessment.fleetservice.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertEquals(cars, result);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.createCar(car);

        assertEquals(car, result);
        assertNull(car.getDriver()); // No driver assigned
    }

    @Test
    void testDeleteCar() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> carService.deleteCar(carId));
        assertNull(car.getDriver()); // Driver should be unassigned
        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    void testGetCarById() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = carService.getCarById(carId);

        assertEquals(car, result);
    }
}

