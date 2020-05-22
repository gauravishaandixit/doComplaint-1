package com.doComplaint.demand;


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
public class DemandController {

    @Autowired
    DemandService demandService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/addDemand",method = RequestMethod.POST)
    public ModelAndView addNewDemand(RedirectAttributes redirectAttributes, HttpSession httpSession, HttpServletRequest request) throws IOException, ServletException, IOException, ServletException {
        ModelAndView modelAndView = null;
        Demand demand = new Demand();

        Student student = studentService.findStudentByUsername(httpSession.getAttribute("username").toString());

        demand.setItem_name(request.getParameter("item_name"));
        demand.addStudent(student);
        demand.setShortDesc(request.getParameter("shortDesc"));
        demand.setTimestamp(new Timestamp(new Date().getTime()));

        //String root = getServletContext().getRealPath("/");
        Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
        File fileToSave = new File("/zprojectimages/request/"+filePart.getSubmittedFileName());
        Files.copy(fileInputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //script command" sudo chown dhruvin:dhruvin /zprojectimages/rent/"
        String fileurl = "http://localhost:8090/downloadFile/request/"+filePart.getSubmittedFileName();
        demand.setImg_url(fileurl);

        boolean flag = demandService.addDemand(demand);
        if(flag)
        {
            redirectAttributes.addFlashAttribute("status","Demand Added");
            modelAndView = new ModelAndView("redirect:/getYourDemands");
        }
        return modelAndView;
    }

    @RequestMapping("/getAllDemands")
    public ModelAndView getAllDemands(HttpSession session){
        //if(session.getAttribute("username") == null)
         //   return new ModelAndView("redirect:/student/login");

        ModelAndView modelAndView = new ModelAndView("studentDemand");

        List<Demand> sells = demandService.findAll();
        modelAndView.addObject("data",sells);
        return modelAndView;
    }

    @RequestMapping("/getYourDemands")
    public ModelAndView getYourDemands(HttpSession session) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(session.getAttribute("rollnumber").toString());

        ModelAndView modelAndView = new ModelAndView("studentDemand");

        List<Demand> demands = new ArrayList<>(student.getDemands());
        Collections.sort(demands, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        modelAndView.addObject("data", demands);
        return modelAndView;
    }

    @GetMapping("/downloadFile/request/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = demandService.loadFileAsResource(fileName);

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
