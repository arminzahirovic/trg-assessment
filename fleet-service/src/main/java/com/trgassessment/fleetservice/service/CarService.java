/**
 * The `CarService` class provides business logic for car-related operations.
 * It interacts with the `CarRepository` and `DriverRepository` to perform CRUD operations
 * on cars and drivers, and it also has methods for assigning/unassigning drivers to/from cars.
 */
package com.trgassessment.fleetservice.service;

import com.trgassessment.fleetservice.DTO.State;
import com.trgassessment.fleetservice.entity.Car;
import com.trgassessment.fleetservice.entity.Driver;
import com.trgassessment.fleetservice.repository.CarRepository;
import com.trgassessment.fleetservice.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public CarService(CarRepository carRepository, DriverRepository driverRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
    }

    /**
     * Get a list of all cars.
     *
     * @return A list of all cars in the database.
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Create a new car and assign driver if provided.
     *
     * @param car The car object to be created.
     * @return The newly created car.
     */
    public Car createCar(Car car) {
        carRepository.save(car);

        if (car.getDriver() != null) {
            assignDriver(car.getId(), car.getDriver().getId());
        }

        return car;
    }

    /**
     * Update an existing car and assign or unassign driver.
     *
     * @param car The updated car object.
     * @return The updated car.
     */
    public Car updateCar(Car car) {
        if (car.getDriver() != null) {
            assignDriver(car.getId(), car.getDriver().getId());
        }

        if (car.getDriver() == null) {
            unassignDriver(car.getId());
        }

        return carRepository.save(car);
    }

    /**
     * Delete a car by its ID.
     *
     * @param carId The ID of the car to delete.
     */
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null && car.getDriver() != null) {
            unassignDriver(car.getId());
        }
        carRepository.deleteById(carId);
    }

    /**
     * Get a car by its ID.
     *
     * @param carId The ID of the car to retrieve.
     * @return The car if found, otherwise null.
     */
    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }

    /**
     * Assign a driver to a car.
     *
     * @param carId    The ID of the car to which the driver will be assigned.
     * @param driverId The ID of the driver to be assigned to the car.
     * @return The state after assigning the driver.
     * @throws NotFoundException If the car or driver is not found.
     */
    public State assignDriverToCar(Long carId, Long driverId) throws NotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(NotFoundException::new);
        Driver driver = driverRepository.findById(driverId).orElseThrow(NotFoundException::new);

        car.setDriver(driver);
        carRepository.save(car);

        return assignDriver(carId, driverId);
    }

    /**
     * Unassign a driver from a car.
     *
     * @param carId The ID of the car from which the driver will be unassigned.
     * @return The state after unassigning the driver.
     * @throws NotFoundException If the car is not found.
     */
    public State unassignDriverFromCar(Long carId) throws NotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(NotFoundException::new);

        car.setDriver(null);
        carRepository.save(car);

        return unassignDriver(carId);
    }

    /**
     * Get cars by a list of IDs.
     *
     * @param carsIds A list of car IDs to filter by.
     * @return A map of car IDs to car objects.
     */
    public Map<Long, Car> getCarsByListOfIds(List<Long> carsIds) {
        List<Car> cars = carRepository.findAllByIds(carsIds);
        return cars.stream().collect(Collectors.toMap(Car::getId, Function.identity()));
    }

    /**
     * Private method `assignDriver` is responsible for sending a request to the fleet-simulator
     * service to assign a driver to a specific car. It takes two parameters: `carId` (the ID of the car
     * to which the driver will be assigned) and `driverId` (the ID of the driver to be assigned to the car).
     *
     * This method creates a POST request to the fleet-simulator service's API endpoint for assigning a driver
     * to a car. It then retrieves the response, which contains the updated state information of the car after
     * the driver has been assigned. This updated state is wrapped in a `State` object.
     *
     * This method is used internally within the `CarService` class when a driver is assigned to a car. It ensures
     * that the fleet-simulator service is informed about the driver assignment and receives the updated car state.
     *
     * @param carId    The ID of the car to which the driver will be assigned.
     * @param driverId The ID of the driver to be assigned to the car.
     * @return The updated state of the car after the driver has been assigned.
     */
    private State assignDriver(Long carId, Long driverId) {
        return WebClient.create().post()
                .uri("http://fleet-simulator:8081/api/states/cars/" + carId + "/assign-driver/" + driverId)
                .retrieve()
                .bodyToMono(State.class)
                .block();
    }

    /**
     * Private method `unassignDriver` is responsible for sending a request to the fleet-simulator
     * service to unassign a driver from a specific car. It takes a single parameter: `carId` (the ID of the car
     * from which the driver will be unassigned).
     *
     * This method creates a POST request to the fleet-simulator service's API endpoint for unassigning a driver
     * from a car. It then retrieves the response, which contains the updated state information of the car after
     * the driver has been unassigned. This updated state is wrapped in a `State` object.
     *
     * This method is used internally within the `CarService` class when a driver is unassigned from a car.
     * It ensures that the fleet-simulator service is informed about the driver unassignment and receives
     * the updated car state.
     *
     * @param carId The ID of the car from which the driver will be unassigned.
     * @return The updated state of the car after the driver has been unassigned.
     * @throws NotFoundException If the car is not found.
     */
    private State unassignDriver(Long carId) {
        return WebClient.create().post()
                .uri("http://fleet-simulator:8081/api/states/cars/" + carId + "/unassign-driver")
                .retrieve()
                .bodyToMono(State.class)
                .block();
    }
}
