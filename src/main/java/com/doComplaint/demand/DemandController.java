package com.doComplaint.demand;


import com.doComplaint.student.Student;
import com.doComplaint.student.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
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
    @Autowired
    DemandTable demandTable;

    @RequestMapping(value = "/addDemand",method = RequestMethod.POST)
    public Long addNewDemand(@RequestBody JsonNode jsonNode) throws IOException {
        System.out.println("Username:: "+ jsonNode.get("id").toString());
        System.out.println("Username:: "+ jsonNode.get("title").textValue());
        System.out.println("Username:: "+ jsonNode.get("shortDesc").textValue());
        System.out.println("Username:: "+ jsonNode.get("imgUrl").toString());
        System.out.println("Username:: "+ jsonNode.get("enrollNo").textValue());
        System.out.println();

        Demand demand = new Demand();

        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());

        demand.setItem_name(jsonNode.get("title").textValue());
        demand.addStudent(student);
        demand.setShortDesc(jsonNode.get("shortDesc").textValue());
        demand.setTimestamp(new Timestamp(new Date().getTime()));
        demand.setImg_url(jsonNode.get("imgUrl").textValue());

        Long flag = demandService.addDemand(demand);

        return flag;
    }

    @RequestMapping("/getAllDemands")
    public List<DemandTable> getAllDemands(HttpSession session){
        //if(session.getAttribute("username") == null)
         //   return new ModelAndView("redirect:/student/login");

        List<Demand> demands = demandService.findAll();
        List<DemandTable> demandTables =demandTable.changeStructure(demands);
        return demandTables;
    }

    @RequestMapping(value = "/updateDemand",method = RequestMethod.POST)
    public Long updateDemand(@RequestBody JsonNode jsonNode){
        Demand demand = demandService.findById(jsonNode.get("id").asLong());

        demand.setItem_name(jsonNode.get("title").textValue());
        demand.setShortDesc(jsonNode.get("shortDesc").textValue());
        demand.setTimestamp(new Timestamp(new Date().getTime()));
        demand.setImg_url(jsonNode.get("imgUrl").textValue());

        Long flag = demandService.updateDemand(demand);
        return flag;
    }

    @RequestMapping(value = "/deleteDemand",method = RequestMethod.POST)
    public boolean deleteDemand(@RequestBody JsonNode jsonNode) throws IOException{
        Long id = jsonNode.get("id").asLong();
        boolean flag = demandService.deleteSell(id);
        return flag;
    }



    @RequestMapping(value = "/getYourDemands",method = RequestMethod.POST)
    public List<DemandTable> getYourDemands(@RequestBody JsonNode jsonNode) {
        //if(session.getAttribute("username") == null)
          //  return new ModelAndView("redirect:/student/login");

        Student student = studentService.findStudentByRollnumber(jsonNode.get("enrollNo").textValue());


        List<Demand> demands = new ArrayList<>(student.getDemands());
        Collections.sort(demands, (c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()));
        List<DemandTable> demandTables = demandTable.changeStructure(demands);
        return demandTables;
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
