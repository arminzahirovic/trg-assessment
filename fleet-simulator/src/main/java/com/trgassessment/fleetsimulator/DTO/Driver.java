/**
 * The `Driver` class represents a data transfer object (DTO) for driver information in the fleet simulator.
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
public class Driver {
    /**
     * The unique identifier for the driver.
     */
    private Long id;

    /**
     * The name of the driver.
     */
    private String name;
}
