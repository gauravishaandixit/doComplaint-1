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

    @RequestMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("admin", "adminloginform", new Admin());
    }

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public ModelAndView loginCheck(@ModelAttribute("adminloginform") Admin admin, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView model;
        Boolean isExist = adminService.doesAdminExists(admin);
        if (isExist) {
            model = new ModelAndView("redirect:/admin/complaints");
            session.setAttribute("username", admin.getUsername());
        } else {
            model = new ModelAndView("redirect:/admin/login");
            redirectAttributes.addFlashAttribute("nouser", "User Not Found or Wrong Username/Password");
        }
        return model;
    }

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        return new ModelAndView("adminRegister", "adminregisterform", new Admin());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addAdmin(@ModelAttribute("adminregisterform") Admin admin, HttpServletRequest request) {
        ModelAndView model = null;

        if (request.getParameter("rootpassword").equals("iiitbadmin")) {
            boolean exists = adminService.addAdmin(admin);
            if (exists) {
                model = new ModelAndView("adminRegister", "adminregisterform", new Admin());
                model.addObject("result", "User/Mobile Already Exists");
            } else {
                model = new ModelAndView("adminRegister", "adminregisterform", new Admin());
                model.addObject("result", "Registered Successfully");
            }
        } else {
            model = new ModelAndView("adminRegister", "adminregisterform", new Admin());
            model.addObject("result", "Wrong Root Password");
        }
        return model;
    }

    @RequestMapping("/complaints")
    ModelAndView getUnresolvedComplaints(HttpSession session) {

        if (session.getAttribute("username") == null)
            return new ModelAndView("redirect:/admin/login");
        List<Complaint> complaints = adminService.findByStatus();;
        Collections.sort(complaints, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));

        List<ComplaintTable> complaintTableList = complaintTable.changeStructure(complaints);
        ModelAndView model = new ModelAndView("allComplaints");
        model.addObject("data", complaintTableList);
        return model;
    }

    @RequestMapping("/complaints/all")
    ModelAndView findAll(HttpSession session)
    {
        if (session.getAttribute("username") == null)
            return new ModelAndView("redirect:/admin/login");
        List<Complaint> complaints = adminService.findAll();

        Collections.sort(complaints, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));

        List<ComplaintTable> complaintTableList = complaintTable.changeStructure(complaints);
        ModelAndView model = new ModelAndView("allComplaints");
        model.addObject("data", complaintTableList);
        return model;
    }
    @RequestMapping("/logout")
    ModelAndView logMeOut(HttpSession session, RedirectAttributes attributes) {
        ModelAndView model = null;
        model = new ModelAndView("redirect:/admin/login");
        attributes.addFlashAttribute("loggedout", "Successfully Logged Out");
        session.invalidate();
        return model;
    }

    @RequestMapping("/update/{id}")
    ModelAndView updateStatus(@PathVariable Long id)
    {
        adminService.updateStatus(id);
        return  new ModelAndView("redirect:/admin/complaints");
    }
}
