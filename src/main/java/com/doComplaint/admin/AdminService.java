package com.doComplaint.admin;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.complaints.ComplaintRepository;
import com.doComplaint.complaints.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ComplaintService complaintService;

    public boolean doesAdminExists(Admin admin)
    {
        Admin admin1 = adminRepository.findAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        return admin1 == null ? false : true;
    }

    public boolean addAdmin(Admin admin)
    {
        Admin admin1 = adminRepository.findAdminByUsername(admin.getUsername());
        if(admin1 == null)
        {
            Admin admin2 = adminRepository.findAdminByMobile(admin.getMobile());
            if(admin2 == null)
            {
                adminRepository.save(admin);
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }

    public List<Complaint> findAll()
    {
        return complaintService.findAll();
    }

    public String updateStatus(Long id)
    {

        Complaint complaint = complaintService.findById(id);

        if(complaint == null)
            return "Not Found";

        if(complaint.getStatus().equals("resolved"))
            complaint.setStatus("unresolved");
        else
            complaint.setStatus("resolved");
        complaintService.addComplaint(complaint);
        return complaint.getStatus();
    }


    public List<Complaint> findByStatus()
    {
        return complaintService.findResolved();
    }
}