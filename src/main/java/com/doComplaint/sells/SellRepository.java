package com.doComplaint.sells;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SellRepository extends CrudRepository<Sell, Long> {
    Optional<Sell> findById(Long id);
}
