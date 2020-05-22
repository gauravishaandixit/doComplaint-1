package com.doComplaint.complaints;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {


    @Autowired
    ComplaintRepository complaintRepository;

    public boolean addComplaint(Complaint complaint)
    {
        complaintRepository.save(complaint);
        return true;
    }

    public List<Complaint> findResolved()
    {
        return complaintRepository.findComplaintByStatusStartingWith("un");
    }

    public List<Complaint> findAll()
    {
        return (List<Complaint>) complaintRepository.findAll();
    }

    public Complaint findById(Long id)
    {
        return complaintRepository.findById(id).orElse(null);
    }
}
