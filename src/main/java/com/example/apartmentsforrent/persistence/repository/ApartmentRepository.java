package com.example.apartmentsforrent.persistence.repository;

import com.example.apartmentsforrent.persistence.model.Apartment;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
    List<Apartment> search(BigDecimal priceFrom, BigDecimal priceTo, Integer quantityOfRoomsFrom, Integer quantityOfRoomsTo,
                           Float areaFrom, Float areaTo, Integer floorFrom, Integer floorTo, Year buildYearFrom, Year buildYearTo);
}
