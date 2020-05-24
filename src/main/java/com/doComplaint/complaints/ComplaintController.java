package com.doComplaint.complaints;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

@CrossOrigin("*")
@RestController
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;
    @Autowired
    StudentService studentService;


    @RequestMapping("/showComplaints")
    public ModelAndView showComplaintPage()
    {
        return new ModelAndView("studentComplaints");
    }

    @RequestMapping(value = "/addComplaint", method = RequestMethod.POST)
    public String addNewComplaint(@RequestBody StudentComplaint studentComplaint)
    {
        Complaint complaint = new Complaint();
        System.out.println("Add Complaint Username:: "+ studentComplaint.getRollnumber());

        Student student = studentService.findStudentByRollnumber(studentComplaint.getRollnumber());

        complaint.setIssue(studentComplaint.getIssue());
        complaint.addStudent(student);
        complaint.setStatus("unresolved");
        complaint.setTimestamp(new Timestamp(new Date().getTime()));

        boolean flag = complaintService.addComplaint(complaint);
        return "Added Successfully.";
    }
}
