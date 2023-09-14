/**
 * The `State` class represents a DTO (Data Transfer Object) for tracking the state of a car.
 * It includes information such as car ID, longitude, latitude, speed, and driver ID.
 *
 * @Getter Generates getter methods for all fields.
 * @Setter Generates setter methods for all fields.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates an all-argument constructor.
 */
package com.trgassessment.fleetservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State {
    /**
     * The ID of the car associated with this state.
     */
    private Long carId;

    /**
     * The longitude of the car's current position.
     */
    private Double longitude;

    /**
     * The latitude of the car's current position.
     */
    private Double latitude;

    /**
     * The speed of the car.
     */
    private Double speed;

    /**
     * The ID of the driver associated with the car, if any.
     */
    private Long driverId;
}
