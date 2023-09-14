/**
 * The `StateRepository` interface is responsible for defining data access operations for
 * the `State` entity in the fleet simulator.
 */
package com.trgassessment.fleetsimulator.repository;

import com.trgassessment.fleetsimulator.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    /**
     * Find all active states where a driver is assigned.
     *
     * @return A list of all active states.
     */
    @Query(value = "FROM State s WHERE s.driverId != null")
    List<State> findAllActive();
}
