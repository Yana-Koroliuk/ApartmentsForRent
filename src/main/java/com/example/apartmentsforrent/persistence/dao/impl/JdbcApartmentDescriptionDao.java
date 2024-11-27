package com.example.apartmentsforrent.persistence.dao.impl;

import com.example.apartmentsforrent.persistence.dao.ApartmentDescriptionDao;
import com.example.apartmentsforrent.persistence.dao.SqlConstants;
import com.example.apartmentsforrent.persistence.dao.mapper.ApartmentDescriptionRowMapper;
import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.persistence.model.BuildingType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class JdbcApartmentDescriptionDao implements ApartmentDescriptionDao {
    private final JdbcTemplate template;

    public JdbcApartmentDescriptionDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ApartmentDescription> findByBuildingType(BuildingType buildingType) {

        return template.query(connection -> {
            PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_DESCRIPTION_BY_BUILDING_TYPE);
            statement.setString(1, buildingType.toString());
            return statement;
        }, new ApartmentDescriptionRowMapper());
    }

    @Override
    public ApartmentDescription create(ApartmentDescription entity) {
        String condition = entity.getCondition();
        BuildingType buildingType = entity.getBuildingType();
        String additionalInfo = entity.getAdditionalInfo();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_DESCRIPTION, new String[]{"id"});
            statement.setString(1, condition);
            statement.setString(2, buildingType.toString());
            statement.setString(3, additionalInfo);
            return statement;
            }, keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Optional<ApartmentDescription> read(Long id) {
        List<ApartmentDescription> result = template.query(connection -> {
            PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_DESCRIPTION_BY_ID);
            statement.setLong(1, id);
            return statement;
        }, new ApartmentDescriptionRowMapper());

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    @Override
    public void update(ApartmentDescription entity) {
        String condition = entity.getCondition();
        BuildingType buildingType = entity.getBuildingType();
        String additionalInfo = entity.getAdditionalInfo();

        template.update(SqlConstants.UPDATE_DESCRIPTION_BY_ID, condition, buildingType.toString(), additionalInfo, entity.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(SqlConstants.DELETE_DESCRIPTION_BY_ID, id);
    }
}
