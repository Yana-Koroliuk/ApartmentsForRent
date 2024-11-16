package com.example.apartmentsforrent.persistence.dao.mapper;

import com.example.apartmentsforrent.persistence.model.Apartment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentRowMapper implements RowMapper<Apartment> {

    @Override
    public Apartment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Apartment.Builder()
                .id(rs.getLong("id"))
                .apartmentDetailsId(rs.getLong("details_id"))
                .apartmentDescriptionId(rs.getLong("description_id"))
                .ownerId(rs.getLong("owner_id"))
                .build();
    }
}
