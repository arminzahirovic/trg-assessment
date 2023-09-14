/**
 * The `State` class represents an entity for storing car state information in the fleet simulator.
 *
 * @Entity Indicates that this class is a JPA entity.
 * @Table Specifies the name of the database table for storing state information.
 * @Getter Generates getter methods for all fields.
 * @Setter Generates setter methods for all fields.
 * @AllArgsConstructor Generates an all-argument constructor.
 * @NoArgsConstructor Generates a no-argument constructor.
 */
package com.trgassessment.fleetsimulator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "State")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class State {
    /**
     * The unique identifier for the car associated with the state.
     */
    @Id
    @Column(name="car_id")
    private Long carId;

    /**
     * The longitude of the car's current location.
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * The latitude of the car's current location.
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * The speed of the car.
     */
    @Column(name = "speed")
    private Double speed;

    /**
     * The unique identifier for the driver associated with the car's state.
     */
    @Column(name = "driver_id")
    private Long driverId;
}
