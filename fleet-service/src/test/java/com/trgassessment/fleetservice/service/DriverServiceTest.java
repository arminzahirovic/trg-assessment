package com.trgassessment.fleetservice.service;

import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDrivers() {
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.getAllDrivers();

        assertEquals(drivers, result);
    }

    @Test
    void testGetAvailableDrivers() {
        Long includedCarId = 1L;
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverRepository.findAvailableDrivers(includedCarId)).thenReturn(drivers);

        List<Driver> result = driverService.getAvailableDrivers(includedCarId);

        assertEquals(drivers, result);
    }

    @Test
    void testGetAvailableDriversWithoutIncludedCar() {
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverRepository.findAvailableDrivers()).thenReturn(drivers);

        List<Driver> result = driverService.getAvailableDrivers(null);

        assertEquals(drivers, result);
    }

    @Test
    void testSaveDriver() {
        Driver driver = new Driver();
        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.saveDriver(driver);

        assertEquals(driver, result);
    }

    @Test
    void testDeleteDriver() {
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        assertDoesNotThrow(() -> driverService.deleteDriver(driverId));
        verify(driverRepository, times(1)).deleteById(driverId);
    }

    @Test
    void testGetDriverById() {
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        Driver result = driverService.getDriverById(driverId);

        assertEquals(driver, result);
    }

    @Test
    void testGetDriversByListOfIds() {
        List<Long> driverIds = Arrays.asList(1L, 2L);
        List<Driver> drivers = Arrays.asList(new Driver(1L, "John", null), new Driver(2L, "Sam", null));

        when(driverRepository.findAllByIds(driverIds)).thenReturn(drivers);

        Map<Long, Driver> result = driverService.getDriversByListOfIds(driverIds);

        assertEquals(drivers.size(), result.size());
        for (Driver driver : drivers) {
            assertTrue(result.containsKey(driver.getId()));
            assertEquals(driver, result.get(driver.getId()));
        }
    }
}
