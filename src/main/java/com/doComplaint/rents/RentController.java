package com.doComplaint.rents;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jdk.internal.util.xml.impl.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
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
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Long addNewRent(@RequestBody JsonNode jsonNode) throws IOException, ServletException {

        System.out.println("Username:: "+ jsonNode.get("id").toString());
        System.out.println("Username:: "+ jsonNode.get("title").textValue());
        System.out.println("Username:: "+ jsonNode.get("shortDesc").textValue());
        System.out.println("Username:: "+ jsonNode.get("description").textValue());
        System.out.println("Username:: "+ jsonNode.get("rent").toString());
        System.out.println("Username:: "+ jsonNode.get("enrollNo").textValue());
        System.out.println();

        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());

        Rent rent = new Rent();
        rent.setItem_name(jsonNode.get("title").textValue());
        rent.addStudent(student);
        rent.setShortDesc(jsonNode.get("shortDesc").textValue());
        rent.setDescription(jsonNode.get("description").textValue());
        rent.setPrice(jsonNode.get("rent").toString());
        rent.setTimestamp(new Timestamp(new Date().getTime()));

        //String root = getServletContext().getRealPath("/");
        /*Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
        File fileToSave = new File("/zprojectimages/rent/"+filePart.getSubmittedFileName());
        Files.copy(fileInputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //script command" sudo chown dhruvin:dhruvin /zprojectimages/rent/"
        String fileurl = "http://localhost:8090/downloadFile/rent/"+filePart.getSubmittedFileName();
        rent.setImg_url(fileurl);*/

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imagename = (timestamp.toString()).replaceAll("[-+.^:, ]","");

        String str = jsonNode.get("imgUrl").toString();
        byte[] imagedata = DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1));
        //BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
        InputStream inputStream = new ByteArrayInputStream(imagedata);

        File fileToSave = new File("/zprojectimages/rent/"+imagename+".jpg");
        Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String temp = "http://localhost:8090/downloadFile/rent/"+imagename+".jpg";
        rent.setImg_url(temp);

        Long flag = rentService.addRent(rent);
        return flag;
    }


    @RequestMapping(value = "/getAllRents",method = RequestMethod.GET)
    public List<RentTable> getAllRents(){
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");
        List<Rent> rents = rentService.findAll();
        List<RentTable> rentTables = rentTable.changeStructure(rents);
        return rentTables;
    }

    @RequestMapping(value = "/updateRent",method = RequestMethod.POST)
    public Long updateRent(@RequestBody JsonNode jsonNode) throws IOException{
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");

        Rent rent = rentService.findById(jsonNode.get("id").asLong());

        rent.setItem_name(jsonNode.get("title").textValue());
        rent.setShortDesc(jsonNode.get("shortDesc").textValue());
        rent.setDescription(jsonNode.get("description").textValue());
        rent.setPrice(jsonNode.get("rent").toString());
        rent.setTimestamp(new Timestamp(new Date().getTime()));

        String curr_imgurl = jsonNode.get("imgUrl").asText();
        if(curr_imgurl.startsWith("data")){

            String u = rent.getImg_url();
            String fileName = u.substring(u.lastIndexOf('/')+1);
            System.out.println(fileName);
            String deleteThis = "/zprojectimages/rent/"+fileName;
            File f = new File(deleteThis);

            boolean st = f.delete();
            System.out.println(st);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String imagename = (timestamp.toString()).replaceAll("[-+.^:, ]","");

            String str = jsonNode.get("imgUrl").toString();
            byte[] imagedata = DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1));
            InputStream inputStream = new ByteArrayInputStream(imagedata);

            File fileToSave = new File("/zprojectimages/rent/"+imagename+".jpg");
            Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String temp = "http://localhost:8090/downloadFile/rent/"+imagename+".jpg";
            rent.setImg_url(temp);
        }
        Long flag = rentService.updateRent(rent);

        return flag;
    }


    @RequestMapping(value = "/deleteRent",method = RequestMethod.POST)
    public boolean deleteRent(@RequestBody JsonNode jsonNode) throws IOException{
        Long id = jsonNode.get("id").asLong();
        Rent rent = rentService.findById(id);
        boolean flag = rentService.deleteRent(id);
        if(flag){
            String u = rent.getImg_url();
            String fileName = u.substring(u.lastIndexOf('/')+1);
            System.out.println(fileName);
            String deleteThis = "/zprojectimages/rent/"+fileName;
            File f = new File(deleteThis);
            boolean st = f.delete();
        }
        return flag;
    }


    /*@RequestMapping(value = "/getAllRents",method = RequestMethod.GET)
    public ResponseEntity<Resource> getAllRents(HttpSession session){
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");
        List<Rent> rents = rentService.findAll();

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for(Rent r :rents){
            JsonObject j = new JsonObject();
            j.addProperty("id",r.getId());
            j.addProperty("title",r.getItem_name());
            j.addProperty("shortDesc",r.getShortDesc());
            j.addProperty("description",r.getDescription());
            jsonArray.add(j);
        }
        jsonObject = jsonArray.getAsJsonObject();
        return ResponseEntity.ok().header(jsonObject.toString()).build();
    }*/


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
