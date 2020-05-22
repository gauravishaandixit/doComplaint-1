package com.doComplaint.complaints;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends CrudRepository<Complaint, Long> {
    Optional<Complaint> findById(Long id);
    List<Complaint> findComplaintByStatusStartingWith(String s);
}
