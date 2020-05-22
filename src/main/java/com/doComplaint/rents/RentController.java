package com.doComplaint.rents;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
public class RentController {

    @Autowired
    RentService rentService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value="/addRent", method = RequestMethod.POST)
    public ModelAndView addNewRent(RedirectAttributes redirectAttributes, HttpSession httpSession,HttpServletRequest request) throws IOException, ServletException {

        ModelAndView modelAndView = null;
        Rent rent = new Rent();
        System.out.println("Username:: "+ httpSession.getAttribute("username").toString());

        Student student = studentService.findStudentByUsername(httpSession.getAttribute("username").toString());

        rent.setItem_name(request.getParameter("item_name"));
        rent.addStudent(student);
        rent.setShortDesc(request.getParameter("shortDesc"));
        rent.setDescription(request.getParameter("description"));
        rent.setPrice(request.getParameter("price"));
        rent.setTimestamp(new Timestamp(new Date().getTime()));

        //String root = getServletContext().getRealPath("/");
        Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
        File fileToSave = new File("/zprojectimages/rent/"+filePart.getSubmittedFileName());
        Files.copy(fileInputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //script command" sudo chown dhruvin:dhruvin /zprojectimages/rent/"
        String fileurl = "http://localhost:8090/downloadFile/rent/"+filePart.getSubmittedFileName();
        rent.setImg_url(fileurl);

        boolean flag = rentService.addRent(rent);
        if(flag)
        {
            redirectAttributes.addFlashAttribute("status","Rent Added");
            modelAndView = new ModelAndView("redirect:/getYourRents");
        }
        return modelAndView;
    }

    /*@RequestMapping("/getAllRents")
    public ModelAndView getAllRents(HttpSession session){
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        ModelAndView modelAndView = new ModelAndView("studentRent");

        List<Rent> rents = rentService.findAll();
        modelAndView.addObject("data",rents);
        return modelAndView;
    }*/

    @RequestMapping(value = "/getAllRents",method = RequestMethod.GET)
    public List<Rent> getAllRents(HttpSession session){
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");
        List<Rent> rents = rentService.findAll();
        return  rents;
    }

    @RequestMapping("/getYourRents")
    public ModelAndView getYourRents(HttpSession session) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(session.getAttribute("rollnumber").toString());

        ModelAndView modelAndView = new ModelAndView("studentRent");

        List<Rent> rents = new ArrayList<>(student.getRents());
        Collections.sort(rents, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        modelAndView.addObject("data", rents);
        return modelAndView;

    }

    @GetMapping("/downloadFile/rent/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = rentService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
