package com.doComplaint.student;


import com.doComplaint.complaints.Complaint;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    String checkLogin(@RequestBody Student student)
    {
        boolean exists = studentService.doesStudentExists(student);
        System.out.println("Hello "+ exists);
        if(exists)
        {
            return "TRUE";
        }
        return "FALSE";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addStudent(@RequestBody Student student)
    {
        Student student1 = studentService.findStudentByRollnumber(student.getRollnumber());
        if(student1 == null)
        {
            student.setImgUrl("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
            studentService.addStudent(student);
            return "Registration Successfull.";
        }
        return "User Already Exists!!!";
    }

    @RequestMapping(value = "/viewProfile", method = RequestMethod.POST)
    public StudentTable viewProfile(@RequestBody JsonNode jsonNode)
    {
        Student student1 = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());
        StudentTable studentTables = new StudentTable().changeStructure(student1);
        return studentTables;
    }

    @RequestMapping(value = "/updateProfile",method = RequestMethod.POST)
    public String changePhoto(@RequestBody JsonNode jsonNode) throws IOException {
        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());

        String curr_imgurl = jsonNode.get("imgUrl").asText();
        if(curr_imgurl.startsWith("data")){

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String imagename = (timestamp.toString()).replaceAll("[-+.^:, ]","");

            String str = jsonNode.get("imgUrl").toString();
            byte[] imagedata = DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1));
            InputStream inputStream = new ByteArrayInputStream(imagedata);

            File directory = new File("/zprojectimages/profile");
            if(!directory.exists()){
                directory.mkdir();
            }

            File fileToSave = new File("/zprojectimages/profile/"+imagename+".jpg");
            Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String temp = "http://172.17.0.2:8090/downloadFile/profile/"+imagename+".jpg";
            student.setImgUrl(temp);
        }
        else {
            student.setImgUrl(jsonNode.get("imgUrl").textValue());
        }

        String flag = studentService.updateURL(student);
        return flag;
    }

    @RequestMapping(value = "/yourcomplaints/{rollnumber}", method = RequestMethod.GET)
    public List<StudentComplaintTable> getAllComplaints(@PathVariable String rollnumber)
    {
        System.out.println("you are:: "+rollnumber);
        Student student = studentService.findStudentByRollnumber(rollnumber);

        System.out.println("you are::: "+student.getUsername());
        List<Complaint> complaints = new ArrayList<>(student.getComplaints());
        Collections.sort(complaints, (c1, c2) -> c2.getId().compareTo(c1.getId()));

        List<StudentComplaintTable> studentComplaintTableList = new StudentComplaintTable().changeStructure(complaints);
        return studentComplaintTableList;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateStatus(@RequestBody Complaint complaint)
    {
        System.out.println("In Update");
        System.out.println(complaint.getId());
        String flag = studentService.updateStatus(complaint.getId());
        System.out.println(flag);
        return  flag;
    }

    @RequestMapping(value = "/pullDemand",method = RequestMethod.POST)
    public String sendMail(@RequestBody JsonNode jsonNode){
        Student requester = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());
        Student owner = studentService.findStudentByRollnumber(jsonNode.get("ownerEnrollNo").textValue());

        studentService.sendEmail(owner,requester);

        return "mail send";
    }

    @GetMapping("/downloadFile/profile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = studentService.loadFileAsResource(fileName);

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
