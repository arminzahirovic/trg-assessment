/**
 * The `Car` class represents a data transfer object (DTO) for car information in the fleet simulator.
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
public class Car {
    /**
     * The unique identifier for the car.
     */
    private Long id;

    /**
     * The brand of the car.
     */
    private String brand;

    /**
     * The model of the car.
     */
    private String model;
}
