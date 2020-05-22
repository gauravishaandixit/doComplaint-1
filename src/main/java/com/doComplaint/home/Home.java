package com.doComplaint.home;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin("*")
@RestController
public class Home {

    @RequestMapping(value = {"/","/index"})
    public ModelAndView showIndexPage() {
        return new ModelAndView("index"); }

    @RequestMapping(value={"/student","/student/"})
    ModelAndView badCallToStudent()
    {
        return new ModelAndView("redirect:/student/login");
    }

    @RequestMapping(value = {"/admin", "/admin/"})
    ModelAndView badCallToAdmin() {
        return new ModelAndView("redirect:/admin/login");
    }
}
