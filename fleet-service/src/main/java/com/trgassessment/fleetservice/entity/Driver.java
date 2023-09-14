/**
 * The `Driver` class represents an entity for storing information about a driver.
 *
 * @Entity Indicates that this class is an entity that can be stored in a database.
 * @Table(name="Driver") Specifies the name of the database table for this entity.
 * @Getter Generates getter methods for all fields.
 * @Setter Generates setter methods for all fields.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates an all-argument constructor.
 */
package com.trgassessment.fleetservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    /**
     * The unique identifier for the driver.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the driver.
     */
    @Column(name = "name")
    private String name;

    /**
     * The car associated with the driver (One-to-One relationship).
     * This field is mapped by the "driver" field in the "Car" entity and is ignored when serializing to JSON.
     */
    @JsonIgnore
    @OneToOne(mappedBy = "driver")
    private Car car;
}
