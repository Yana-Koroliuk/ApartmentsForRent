package com.example.apartmentsforrent.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "descriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_type", nullable = false)
    private BuildingType buildingType;

    @Column(name = "additional_info", length = 255)
    private String additionalInfo;
}
