/**
 * The `DriverRepository` interface defines the repository for the `Driver` entity.
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on driver entities and includes custom queries to find drivers by a list of IDs and to
 * find available drivers.
 */
package com.trgassessment.fleetservice.repository;

import com.trgassessment.fleetservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    /**
     * Custom query to find drivers by a list of IDs.
     *
     * @param ids A list of driver IDs to search for.
     * @return A list of driver entities that match the given IDs.
     */
    @Query(value = "FROM Driver d WHERE d.id in ?1")
    List<Driver> findAllByIds(List<Long> ids);

    /**
     * Query to find all available drivers (drivers not associated with any car).
     *
     * @return A list of available driver entities.
     */
    @Query(value = "SELECT d FROM Driver d LEFT JOIN d.car c WHERE c.id IS NULL")
    List<Driver> findAvailableDrivers();

    /**
     * Query to find all available drivers, including those associated with a specific car.
     *
     * @param includedCarId The ID of the car to include in the search for available drivers.
     * @return A list of available driver entities, including those associated with the specified car.
     */
    @Query(value = "SELECT d FROM Driver d LEFT JOIN d.car c WHERE c.id IS NULL OR c.id = :includedCarId")
    List<Driver> findAvailableDrivers(@Param("includedCarId") Long includedCarId);
}
