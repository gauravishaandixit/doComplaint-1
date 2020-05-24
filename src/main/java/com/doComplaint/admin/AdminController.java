package com.doComplaint.admin;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class    AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    ComplaintTable complaintTable;

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public String loginCheck(@RequestBody Admin admin) {
        Boolean isExist = adminService.doesAdminExists(admin);
        if (isExist)
            return "True";
        else
        {
            return "False";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addAdmin(@RequestBody Admin admin) {
        if(!adminService.addAdmin(admin))
            return "Successfully Registered";
        else
            return "Admin or Mobile Number Already Present";
    }

    @RequestMapping("/uncomplaints")
    public List<ComplaintTable> getUnresolvedComplaints() {
        List<Complaint> complaints = adminService.findByStatus();;
        Collections.sort(complaints, (c1, c2) -> c2.getId().compareTo(c1.getId()));

        List<ComplaintTable> complaintTableList = complaintTable.changeStructure(complaints);

        return complaintTableList;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateStatus(@RequestBody Complaint complaint)
    {
        System.out.println("In Update");
        System.out.println(complaint.getId());
        String flag = adminService.updateStatus(complaint.getId());
        System.out.println(flag);
        return  flag;
    }


    @RequestMapping("/allcomplaints")
    public List<ComplaintTable> findAll()
    {
        List<Complaint> complaints = adminService.findAll();

        Collections.sort(complaints, (c1, c2) -> c2.getId().compareTo(c1.getId()));

        List<ComplaintTable> complaintTableList = complaintTable.changeStructure(complaints);
        return complaintTableList;
    }
}
