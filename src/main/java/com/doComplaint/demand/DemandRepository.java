package com.doComplaint.demand;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DemandRepository extends CrudRepository<Demand,Long> {
    Optional<Demand> findById(Long id);
}
