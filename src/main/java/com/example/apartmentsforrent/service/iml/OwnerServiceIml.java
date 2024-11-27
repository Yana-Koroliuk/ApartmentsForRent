package com.example.apartmentsforrent.service.iml;

import com.example.apartmentsforrent.persistence.dao.impl.JdbcOwnerDao;
import com.example.apartmentsforrent.persistence.model.Owner;
import com.example.apartmentsforrent.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerServiceIml implements OwnerService {
    JdbcOwnerDao ownerDao;

    @Autowired
    public void setOwnerDao(JdbcOwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    @Override
    public Owner createOwner(Owner owner) {
        return ownerDao.create(owner);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerDao.update(owner);
    }

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerDao.read(id);
    }

    @Override
    public Optional<Owner> getOwnerByEmail(String email) {
        return ownerDao.findByEmail(email);
    }

    @Override
    public void deleteOwner(Long id) {
        if (ownerDao.read(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("Owner with id %s does not exist", id));
        }
        ownerDao.delete(id);
    }

    @Override
    public Boolean isEmailTaken(String email) {
        return ownerDao.findByEmail(email).isPresent();
    }
}
