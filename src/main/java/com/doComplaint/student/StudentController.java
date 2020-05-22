package com.doComplaint.student;


import com.doComplaint.complaints.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/login")
    ModelAndView showLoginPage()
    {
        return new ModelAndView("student","studentloginform", new Student());
    }

    @RequestMapping("/logincheck")
    ModelAndView checkLogin(@ModelAttribute("studentloginform") Student student, HttpSession session, RedirectAttributes redirectAttributes)
    {
        ModelAndView model = null;
        boolean exists = studentService.doesStudentExists(student);
        if(exists)
        {
            model = new ModelAndView("redirect:/student/yourcomplaints");
            session.setAttribute("username", student.getUsername());

            student = studentService.findStudentByUsername(student.getUsername());
            session.setAttribute("rollnumber", student.getRollnumber());
        }
        else
        {
            model = new ModelAndView("redirect:/student/login");
            redirectAttributes.addFlashAttribute("nouser", "User Not Found or Wrong Username/Password");
        }
        return model;
    }

    @RequestMapping("/register")
    public ModelAndView showRegisterPage()
    {
        return new ModelAndView("studentRegister","studentregisterform", new Student());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addStudent(@ModelAttribute("studentregisterform") Student student)
    {
        ModelAndView model = null;
        boolean exists = studentService.addStudent(student);
        if(!exists)
        {
            model = new ModelAndView("studentRegister","studentregisterform",new Student());
            model.addObject("result","Registered Successfully");
        }
        else
        {
            model = new ModelAndView("studentRegister","studentregisterfrom", new Student());
            model.addObject("result","User Already Exists");
        }
        return model;
    }

    @RequestMapping("/yourcomplaints")
    public ModelAndView getAllComplaints(HttpSession session)
    {
        if(session.getAttribute("username") == null)
            return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(session.getAttribute("rollnumber").toString());

        ModelAndView modelAndView = new ModelAndView("studentComplaints");

        List<Complaint> complaints = new ArrayList<>(student.getComplaints());
        Collections.sort(complaints, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        modelAndView.addObject("data",complaints);
        return modelAndView;
    }

    @RequestMapping("/logout")
    ModelAndView logMeOut(HttpSession session, RedirectAttributes attributes)
    {
        ModelAndView model = null;
        model = new ModelAndView("redirect:/student/login");
        attributes.addFlashAttribute("loggedout","Successfully Logged Out");
        session.invalidate();
        return model;
    }

    @RequestMapping("/tradeView")
    public ModelAndView getTradeView(HttpSession httpSession){
        if(httpSession.getAttribute("username") == null ){
            return new ModelAndView("redirect:/student/login");
        }
         ModelAndView modelAndView = new ModelAndView("studentRent");

        return modelAndView;
    }
}
