package com.example.apartmentsforrent.service;

import com.example.apartmentsforrent.persistence.model.Apartment;
import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import com.example.apartmentsforrent.persistence.model.Owner;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface ApartmentService {
    Apartment create(Apartment apartment);
    Apartment update(Apartment apartment);
    void deleteById(Long id);
    Optional<Apartment> findById(Long id);
    List<Apartment> findAll();
    List<Apartment> getAllWithFiltering(int page, int size, BigDecimal priceFrom, BigDecimal priceTo, Integer quantityOfRoomsFrom, Integer quantityOfRoomsTo,
                           Float areaFrom, Float areaTo, Integer floorFrom, Integer floorTo, Year buildYearFrom, Year buildYearTo);
}