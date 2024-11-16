package com.example.apartmentsforrent.persistence.dao.mapper;

import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

public class ApartmentDetailsRowMapper implements RowMapper<ApartmentDetails> {
    @Override
    public ApartmentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ApartmentDetails.Builder()
                .id(rs.getLong("id"))
                .address(rs.getString("address"))
                .buildYear(Year.of(rs.getDate("year").toLocalDate().getYear()))
                .price(rs.getBigDecimal("price"))
                .floor(rs.getInt("floor"))
                .area(rs.getFloat("area"))
                .quantityOfRooms(rs.getInt("quantity_of_rooms"))
                .build();
    }
}
