package com.example.apartmentsforrent.persistence.repository;

import com.example.apartmentsforrent.persistence.entity.ApartmentDescription;
import com.example.apartmentsforrent.persistence.entity.BuildingType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentDescriptionRepository extends CrudRepository<ApartmentDescription, Long> {
    @Query("SELECT a FROM ApartmentDescription a WHERE a.buildingType = :buildingType")
    List<ApartmentDescription> findByBuildingType(@Param("buildingType") BuildingType buildingType);
}
