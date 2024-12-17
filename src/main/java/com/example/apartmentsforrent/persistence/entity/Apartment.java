package com.example.apartmentsforrent.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(name = "Apartment.findByDetailsId", query = "SELECT a FROM Apartment a WHERE a.apartmentDetails.id = :detailsId"),
        @NamedQuery(name = "Apartment.findByDescriptionId", query = "SELECT a FROM Apartment a WHERE a.apartmentDescription.id = :descriptionId")
})
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "details_id", nullable = false)
    private ApartmentDetails apartmentDetails;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "description_id", nullable = false)
    private ApartmentDescription apartmentDescription;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
}
