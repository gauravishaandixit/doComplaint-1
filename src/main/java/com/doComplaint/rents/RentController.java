package com.doComplaint.rents;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import jdk.internal.util.xml.impl.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
    @Autowired
    RentTable rentTable;

    @RequestMapping(value="/addRent", method = RequestMethod.POST)
    public Long addNewRent(RedirectAttributes redirectAttributes, HttpSession httpSession,HttpServletRequest request) throws IOException, ServletException {

        Rent rent = new Rent();
        System.out.println("Username:: "+ httpSession.getAttribute("username").toString());

        //Student student = studentService.findStudentByUsername(httpSession.getAttribute("username").toString());
        Student student = studentService.findStudentByUsername("dhruvin");

        rent.setItem_name(request.getParameter("item_name"));
        rent.addStudent(student);
        rent.setShortDesc(request.getParameter("shortDesc"));
        rent.setDescription(request.getParameter("description"));
        rent.setPrice(request.getParameter("price"));
        rent.setTimestamp(new Timestamp(new Date().getTime()));

        //String root = getServletContext().getRealPath("/");
        /*Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
        File fileToSave = new File("/zprojectimages/rent/"+filePart.getSubmittedFileName());
        Files.copy(fileInputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //script command" sudo chown dhruvin:dhruvin /zprojectimages/rent/"
        String fileurl = "http://localhost:8090/downloadFile/rent/"+filePart.getSubmittedFileName();
        rent.setImg_url(fileurl);*/

        String str = request.getParameter("image");
        byte[] imagedata = DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1));
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
        InputStream inputStream = new ByteArrayInputStream(imagedata);

        File fileToSave = new File("/zprojectimages/rent/"+"img.png");
        Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Long flag = rentService.addRent(rent);
        if(flag != 0)
        {
            redirectAttributes.addFlashAttribute("status","Rent Added");
        }
        return flag;
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
    public List<RentTable> getAllRents(HttpSession session){
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");
        List<Rent> rents = rentService.findAll();
        List<RentTable> rentTables = rentTable.changeStructure(rents);
        return rentTables;
    }

    @RequestMapping("/getYourRents")
    public List<RentTable> getYourRents(HttpSession session) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        //Student student = studentService.findStudentByRollnumber(session.getAttribute("rollnumber").toString());
        Student student = studentService.findStudentByRollnumber("MT2019035");

        List<Rent> rents = new ArrayList<>(student.getRents());
        Collections.sort(rents, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));

        List<RentTable> rentTables = rentTable.changeStructure(rents);
        return rentTables;

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
