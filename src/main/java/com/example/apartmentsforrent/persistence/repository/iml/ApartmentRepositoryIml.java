package com.example.apartmentsforrent.persistence.repository.iml;

import com.example.apartmentsforrent.persistence.Checker;
import com.example.apartmentsforrent.persistence.model.*;
import com.example.apartmentsforrent.persistence.repository.ApartmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ApartmentRepositoryIml implements ApartmentRepository {
    private final HashMap<Long, Apartment> databaseMap = new HashMap<>();
    private long index = 0;

    @PostConstruct
    public void initializeDatabase() {
        Owner owner1 = new Owner.Builder()
                .id(1L)
                .name("Name1")
                .surname("Surname1")
                .email("owner1@gmail.com")
                .phoneNumber("+380239871345")
                .passwordHash("2frg3vb12e56")
                .build();

        Owner owner2 = new Owner.Builder()
                .id(2L)
                .name("Name2")
                .surname("Surname2")
                .email("owner2@gmail.com")
                .phoneNumber("+380239872222")
                .passwordHash("2fr3gvb11234")
                .build();

        ApartmentDescription apartmentDescription1 = new ApartmentDescription.Builder()
                .id(1L)
                .condition("Condition1")
                .buildingType(BuildingType.FRAME)
                .additionalInfo("Additional info1")
                .build();

        ApartmentDescription apartmentDescription2 = new ApartmentDescription.Builder()
                .id(2L)
                .condition("Condition2")
                .buildingType(BuildingType.BRICK)
                .additionalInfo("Additional info2")
                .build();

        ApartmentDetails apartmentDetails1 = new ApartmentDetails.Builder()
                .id(1L)
                .address("St. Bankova, b. 12")
                .buildYear(Year.of(2018))
                .price(new BigDecimal("1500000.50"))
                .floor(4)
                .area(175)
                .quantityOfRooms(4)
                .build();

        ApartmentDetails apartmentDetails2 = new ApartmentDetails.Builder()
                .id(2L)
                .address("St. Peremohy, b. 21")
                .buildYear(Year.of(2016))
                .price(new BigDecimal("2000000.50"))
                .floor(5)
                .area(200)
                .quantityOfRooms(5)
                .build();

        Apartment apartment1 = new Apartment.Builder()
                .apartmentDetails(apartmentDetails1)
                .apartmentDescription(apartmentDescription1)
                .owner(owner1)
                .build();

        Apartment apartment2 = new Apartment.Builder()
                .apartmentDetails(apartmentDetails2)
                .apartmentDescription(apartmentDescription2)
                .owner(owner2)
                .build();

        save(apartment1);
        save(apartment2);
    }

    @Override
    public Apartment save(Apartment apartment) {
        if (apartment.getId() == null) {
            apartment.setId(index);
            databaseMap.put(index, apartment);
            index++;
        } else {
            databaseMap.put(apartment.getId(), apartment);
        }
        return apartment;
    }

    @Override
    public Optional<Apartment> findById(Long id) {
        Apartment entity = databaseMap.get(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Iterable<Apartment> findAll() {
        return databaseMap.values();
    }

    @Override
    public boolean existsById(Long id) {
        return databaseMap.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        databaseMap.remove(id);
    }

    @Override
    public List<Apartment> getAllWithFiltering(int page, int size, BigDecimal priceFrom, BigDecimal priceTo, Integer quantityOfRoomsFrom, Integer quantityOfRoomsTo,
                                               Float areaFrom, Float areaTo, Integer floorFrom, Integer floorTo, Year buildYearFrom, Year buildYearTo) {
        return databaseMap.values().stream()
                .filter(apartment -> checkerProvider().check(priceFrom, priceTo, apartment.getApartmentDetails().getPrice(), BigDecimal.ZERO))
                .filter(apartment -> checkerProvider().check(quantityOfRoomsFrom, quantityOfRoomsTo, apartment.getApartmentDetails().getQuantityOfRooms(), 0))
                .filter(apartment -> checkerProvider().check(areaFrom, areaTo, apartment.getApartmentDetails().getArea(), 0f))
                .filter(apartment -> checkerProvider().check(floorFrom, floorTo, apartment.getApartmentDetails().getFloor(), 0))
                .filter(apartment -> checkerProvider().check(buildYearFrom, buildYearTo, apartment.getApartmentDetails().getBuildYear(), Year.of(0)))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }

    @Override
    public List<Apartment> findAllWithPagination(int page, int size) {
        return databaseMap.values().stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }

    @Bean
    private Checker checkerProvider() {
        return new Checker();
    }
}
