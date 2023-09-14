/**
 * The `StateResponse` class represents a data transfer object (DTO) for car state information
 * in the fleet simulator.
 *
 * @Getter Generates getter methods for all fields.
 * @Setter Generates setter methods for all fields.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates an all-argument constructor.
 */
package com.trgassessment.fleetsimulator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateResponse {
    /**
     * The car associated with the state.
     */
    private Car car;

    /**
     * The longitude of the car's current location.
     */
    private Double longitude;

    /**
     * The latitude of the car's current location.
     */
    private Double latitude;

    /**
     * The speed of the car.
     */
    private Double speed;

    /**
     * The driver associated with the car's state.
     */
    private Driver driver;
}
