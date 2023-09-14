/**
 * The `Car` class represents an entity for storing information about a car.
 *
 * @Entity Indicates that this class is an entity that can be stored in a database.
 * @Table(name="Car") Specifies the name of the database table for this entity.
 * @Getter Generates getter methods for all fields.
 * @Setter Generates setter methods for all fields.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates an all-argument constructor.
 */
package com.trgassessment.fleetservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    /**
     * The unique identifier for the car.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    /**
     * The brand of the car.
     */
    @Column(name = "brand")
    private String brand;

    /**
     * The model of the car.
     */
    @Column(name = "model")
    private String model;

    /**
     * The driver associated with the car (One-to-One relationship).
     * This field is mapped to the "driver_id" column in the database table and references the "id" column
     * in the "Driver" entity.
     */
    @OneToOne()
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;
}
