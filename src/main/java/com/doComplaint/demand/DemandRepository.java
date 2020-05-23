package com.doComplaint.demand;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DemandRepository extends CrudRepository<Demand,Long> {
    Optional<Demand> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Demand d where d.id = :id")
    void deleteByMyId(@Param("id") Long id);
}
