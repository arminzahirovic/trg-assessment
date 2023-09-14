/**
 * The `CarRepository` interface defines the repository for the `Car` entity.
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on car entities and includes a custom query to find cars by a list of IDs.
 */
package com.trgassessment.fleetservice.repository;

import com.trgassessment.fleetservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    /**
     * Custom query to find cars by a list of IDs.
     *
     * @param ids A list of car IDs to search for.
     * @return A list of car entities that match the given IDs.
     */
    @Query(value = "FROM Car c WHERE c.id in ?1")
    List<Car> findAllByIds(List<Long> ids);
}
