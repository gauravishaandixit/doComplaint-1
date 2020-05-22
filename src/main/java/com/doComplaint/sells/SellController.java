package com.doComplaint.sells;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class SellController {
    @Autowired
    SellService sellService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/addSell",method = RequestMethod.POST)
    public ModelAndView addNewSell(RedirectAttributes redirectAttributes, HttpSession httpSession, HttpServletRequest request) throws IOException, ServletException {
        ModelAndView modelAndView = null;
        Sell sell = new Sell();

        Student student = studentService.findStudentByUsername(httpSession.getAttribute("username").toString());

        sell.setItem_name(request.getParameter("item_name"));
        sell.addStudent(student);
        sell.setShortDesc(request.getParameter("shortDesc"));
        sell.setDescription(request.getParameter("description"));
        sell.setPrice(request.getParameter("price"));
        sell.setTimestamp(new Timestamp(new Date().getTime()));

        //String root = getServletContext().getRealPath("/");
        Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
        File fileToSave = new File("/zprojectimages/sell/"+filePart.getSubmittedFileName());
        Files.copy(fileInputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //script command" sudo chown dhruvin:dhruvin /zprojectimages/rent/"
        String fileurl = "http://localhost:8090/downloadFile/sell/"+filePart.getSubmittedFileName();
        sell.setImg_url(fileurl);

        boolean flag = sellService.addSell(sell);
        if(flag)
        {
            redirectAttributes.addFlashAttribute("status","Sell Added");
            modelAndView = new ModelAndView("redirect:/getYourSells");
        }
        return modelAndView;
    }

    @RequestMapping("/getAllSells")
    public ModelAndView getAllSells(HttpSession session){
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        ModelAndView modelAndView = new ModelAndView("studentSell");

        List<Sell> sells = sellService.findAll();
        modelAndView.addObject("data",sells);
        return modelAndView;
    }

    @RequestMapping("/getYourSells")
    public ModelAndView getYourSells(HttpSession session) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(session.getAttribute("rollnumber").toString());

        ModelAndView modelAndView = new ModelAndView("studentSell");

        List<Sell> sells = new ArrayList<>(student.getSells());
        Collections.sort(sells, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        modelAndView.addObject("data", sells);
        return modelAndView;
    }

    @GetMapping("/downloadFile/sell/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = sellService.loadFileAsResource(fileName);

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
