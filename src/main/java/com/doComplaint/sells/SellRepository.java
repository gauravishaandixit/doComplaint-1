package com.doComplaint.sells;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SellRepository extends CrudRepository<Sell, Long> {
    Optional<Sell> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Sell s where s.id = :id")
    void deleteByMyId(@Param("id") Long id);
}
