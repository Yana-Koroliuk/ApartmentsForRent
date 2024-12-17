package com.example.apartmentsforrent.persistence.repository;

import com.example.apartmentsforrent.persistence.entity.ApartmentDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ApartmentDetailsRepository extends CrudRepository<ApartmentDetails, Long> {
    List<ApartmentDetails> findAll();

    @Query(value = "SELECT * FROM details LIMIT :size OFFSET :offset", nativeQuery = true)
    List<ApartmentDetails> getAll(@Param("offset") int offset, @Param("size") int size);

    @Query(value = "SELECT * FROM details d " +
            "WHERE (:priceFrom IS NULL OR d.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR d.price <= :priceTo) " +
            "AND (:roomsFrom IS NULL OR d.quantity_of_rooms >= :roomsFrom) " +
            "AND (:roomsTo IS NULL OR d.quantity_of_rooms <= :roomsTo) " +
            "AND (:areaFrom IS NULL OR d.area >= :areaFrom) " +
            "AND (:areaTo IS NULL OR d.area <= :areaTo) " +
            "AND (:floorFrom IS NULL OR d.floor >= :floorFrom) " +
            "AND (:floorTo IS NULL OR d.floor <= :floorTo) " +
            "AND (:yearOfBuildFrom IS NULL OR d.year >= :yearOfBuildFrom) " +
            "AND (:yearOfBuildTo IS NULL OR d.year <= :yearOfBuildTo) " +
            "LIMIT :size OFFSET :offset",
            nativeQuery = true)
    List<ApartmentDetails> getAllWithFiltering(
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("roomsFrom") Integer quantityOfRoomsFrom,
            @Param("roomsTo") Integer quantityOfRoomsTo,
            @Param("areaFrom") Float areaFrom,
            @Param("areaTo") Float areaTo,
            @Param("floorFrom") Integer floorFrom,
            @Param("floorTo") Integer floorTo,
            @Param("yearOfBuildFrom") Integer yearOfBuildFrom,
            @Param("yearOfBuildTo") Integer yearOfBuildTo
    );

    Optional<ApartmentDetails> findByAddress(String address);
}
