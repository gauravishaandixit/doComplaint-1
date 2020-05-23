package com.doComplaint.rents;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RentRepository extends CrudRepository<Rent, Long> {
    Optional<Rent> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Rent r where r.id = :id")
    void deleteByMyId(@Param("id") Long id);
}
