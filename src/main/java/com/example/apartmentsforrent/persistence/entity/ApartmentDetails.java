package com.example.apartmentsforrent.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;

@Entity
@Table(name = "details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private float area;

    @Column(name = "year", nullable = false)
    private Year buildYear;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int floor;

    @Column(name = "quantity_of_rooms", nullable = false)
    private int quantityOfRooms;
}
