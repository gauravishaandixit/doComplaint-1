package com.doComplaint.rents;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RentRepository extends CrudRepository<Rent, Long> {
    Optional<Rent> findById(Long id);

}
