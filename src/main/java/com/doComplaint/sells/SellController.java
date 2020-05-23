package com.doComplaint.sells;

import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
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
import javax.xml.bind.DatatypeConverter;
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
public class SellController {
    @Autowired
    SellService sellService;
    @Autowired
    StudentService studentService;
    @Autowired
    SellTable sellTable;

    @RequestMapping(value="/addSell", method = RequestMethod.POST)
    public Long addNewSell(@RequestBody JsonNode jsonNode) throws IOException, ServletException {

        System.out.println("Username:: "+ jsonNode.get("id").toString());
        System.out.println("Username:: "+ jsonNode.get("title").textValue());
        System.out.println("Username:: "+ jsonNode.get("shortDesc").textValue());
        System.out.println("Username:: "+ jsonNode.get("description").textValue());
        System.out.println("Username:: "+ jsonNode.get("price").toString());
        System.out.println("Username:: "+ jsonNode.get("enrollNo").textValue());
        System.out.println();

        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());

        Sell sell = new Sell();
        sell.setItem_name(jsonNode.get("title").textValue());
        sell.addStudent(student);
        sell.setShortDesc(jsonNode.get("shortDesc").textValue());
        sell.setDescription(jsonNode.get("description").textValue());
        sell.setPrice(jsonNode.get("price").toString());
        sell.setTimestamp(new Timestamp(new Date().getTime()));

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

        File fileToSave = new File("/zprojectimages/sell/"+imagename+".jpg");
        Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String temp = "http://localhost:8090/downloadFile/sell/"+imagename+".jpg";
        sell.setImg_url(temp);

        Long flag = sellService.addSell(sell);
        return flag;
    }

    @RequestMapping("/getAllSells")
    public List<SellTable> getAllSells(HttpSession session){
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        List<Sell> sells = sellService.findAll();
        List<SellTable> sellTables = sellTable.changeStructure(sells);
        return sellTables;
    }

    @RequestMapping(value = "/updateSell",method = RequestMethod.POST)
    public Long updateSell(@RequestBody JsonNode jsonNode) throws IOException{
        //if(session.getAttribute("username") == null)
        //  return new ModelAndView("redirect:/student/login");

        Sell sell = sellService.findById(jsonNode.get("id").asLong());

        sell.setItem_name(jsonNode.get("title").textValue());
        sell.setShortDesc(jsonNode.get("shortDesc").textValue());
        sell.setDescription(jsonNode.get("description").textValue());
        sell.setPrice(jsonNode.get("price").toString());
        sell.setTimestamp(new Timestamp(new Date().getTime()));

        String curr_imgurl = jsonNode.get("imgUrl").asText();
        if(curr_imgurl.startsWith("data")){

            String u = sell.getImg_url();
            String fileName = u.substring(u.lastIndexOf('/')+1);
            System.out.println(fileName);
            String deleteThis = "/zprojectimages/sell/"+fileName;
            File f = new File(deleteThis);

            boolean st = f.delete();
            System.out.println(st);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String imagename = (timestamp.toString()).replaceAll("[-+.^:, ]","");

            String str = jsonNode.get("imgUrl").toString();
            byte[] imagedata = DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1));
            InputStream inputStream = new ByteArrayInputStream(imagedata);

            File fileToSave = new File("/zprojectimages/sell/"+imagename+".jpg");
            Files.copy(inputStream,fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String temp = "http://localhost:8090/downloadFile/sell/"+imagename+".jpg";
            sell.setImg_url(temp);
        }
        Long flag = sellService.updateSell(sell);

        return flag;
    }

    @RequestMapping(value = "/deleteSell",method = RequestMethod.POST)
    public boolean deleteSell(@RequestBody JsonNode jsonNode) throws IOException{
        Long id = jsonNode.get("id").asLong();
        Sell sell = sellService.findById(id);
        boolean flag = sellService.deleteSell(id);
        if(flag){
            String u = sell.getImg_url();
            String fileName = u.substring(u.lastIndexOf('/')+1);
            System.out.println(fileName);
            String deleteThis = "/zprojectimages/sell/"+fileName;
            File f = new File(deleteThis);
            boolean st = f.delete();
        }
        return flag;
    }







    @RequestMapping(value = "/getYourSells",method = RequestMethod.POST)
    public List<SellTable> getYourSells(@RequestBody JsonNode jsonNode) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());


        List<Sell> sells = new ArrayList<>(student.getSells());
        Collections.sort(sells, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        List<SellTable> sellTables = sellTable.changeStructure(sells);
        return sellTables;
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
