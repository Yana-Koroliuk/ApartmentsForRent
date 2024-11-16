package com.example.apartmentsforrent.persistence.dao;

public class SqlConstants {
    public static final String INSERT_APARTMENT = "INSERT INTO apartments (details_id, description_id, owner_id) VALUES (?, ?, ?)";
    public static final String SELECT_APARTMENT_BY_ID = "SELECT * FROM apartments WHERE apartments.id = ?";
    public static final String UPDATE_APARTMENT_BY_ID = "UPDATE apartments SET details_id = ?, description_id = ?, owner_id = ? WHERE id = ?";
    public static final String DELETE_APARTMENT_BY_ID = "DELETE FROM apartments WHERE id = ?";
    public static final String SELECT_APARTMENT_BY_DETAIL_ID = "SELECT * FROM apartments WHERE details_id = ?";
}