package com.trgassessment.fleetservice.controller;

import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DriverControllerTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDriver() {
        Driver driver = new Driver();
        when(driverService.saveDriver(driver)).thenReturn(driver);

        ResponseEntity<Driver> response = driverController.saveDriver(driver);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(driver, response.getBody());
    }

    @Test
    void testGetAllDrivers() {
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverService.getAllDrivers()).thenReturn(drivers);

        ResponseEntity<List<Driver>> response = driverController.getAllDrivers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(drivers, response.getBody());
    }

    @Test
    void testGetDriverById() {
        Long driverId = 1L;
        Driver driver = new Driver();
        when(driverService.getDriverById(driverId)).thenReturn(driver);

        ResponseEntity<Driver> response = driverController.getDriverById(driverId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driver, response.getBody());
    }

    @Test
    void testGetDriverByIdNotFound() {
        Long driverId = 1L;
        when(driverService.getDriverById(driverId)).thenReturn(null);

        ResponseEntity<Driver> response = driverController.getDriverById(driverId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateDriver() {
        Driver updatedDriver = new Driver();
        when(driverService.saveDriver(updatedDriver)).thenReturn(updatedDriver);

        ResponseEntity<Driver> response = driverController.updateDriver(updatedDriver);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedDriver, response.getBody());
    }

    @Test
    void testDeleteDriver() {
        Long driverId = 1L;
        ResponseEntity<Void> response = driverController.deleteDriver(driverId);

        verify(driverService, times(1)).deleteDriver(driverId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetDriversByIds() {
        List<Long> driverIds = Arrays.asList(1L, 2L);
        Map<Long, Driver> driverMap = Map.of(1L, new Driver(), 2L, new Driver());
        when(driverService.getDriversByListOfIds(driverIds)).thenReturn(driverMap);

        ResponseEntity<Map<Long, Driver>> response = driverController.getDriversByIds(driverIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverMap, response.getBody());
    }

    @Test
    void testGetDriversByIdsNotFound() {
        List<Long> driverIds = Arrays.asList(1L, 2L);
        when(driverService.getDriversByListOfIds(driverIds)).thenReturn(null);

        ResponseEntity<Map<Long, Driver>> response = driverController.getDriversByIds(driverIds);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAvailableDrivers() {
        Long includedCarId = 1L;
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverService.getAvailableDrivers(includedCarId)).thenReturn(drivers);

        ResponseEntity<List<Driver>> response = driverController.getAvailableDrivers(includedCarId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(drivers, response.getBody());
    }
}
