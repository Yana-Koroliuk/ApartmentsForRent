package com.example.apartmentsforrent.persistence.repository;

import com.example.apartmentsforrent.persistence.entity.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    @Query(value = "SELECT * FROM owners WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<Owner> findByEmail(@Param("email") String email);
}
