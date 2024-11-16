package com.example.apartmentsforrent.persistence.dao.mapper;

import com.example.apartmentsforrent.persistence.model.Apartment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentRowMapper implements RowMapper<Apartment> {

    @Override
    public Apartment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Apartment.Builder()
                .id(resultSet.getLong("id"))
                .apartmentDetailsId(resultSet.getLong("details_id"))
                .apartmentDescriptionId(resultSet.getLong("description_id"))
                .ownerId(resultSet.getLong("owner_id"))
                .build();
    }
}
