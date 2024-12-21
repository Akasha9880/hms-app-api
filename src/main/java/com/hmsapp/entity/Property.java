package com.hmsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "no_of_bedrooms")
    private Integer noOfBedrooms;

    @Column(name = "no_of_bathrooms")
    private Integer noOfBathrooms;

    @Column(name = "no_of_guests")
    private Integer noOfGuests;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}