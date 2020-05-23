package com.doComplaint.demand;

import com.doComplaint.student.Student;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class DemandTable {
    private Long id;
    private Timestamp timestamp;
    private String title;
    private String shortDesc;
    private String img_url;
    private String username;
    private String rollno;
    private String roomnumber;
    private String mobilenumber;

    List<DemandTable> changeStructure(List<Demand> Demands)
    {
        List<DemandTable> demandTables = new ArrayList<DemandTable>();
        for(Demand demand: Demands)
        {
            DemandTable demandTable = new DemandTable();

            demandTable.setId(demand.getId());
            demandTable.setTimestamp(demand.getTimestamp());
            demandTable.setShortDesc(demand.getShortDesc());
            demandTable.setTitle(demand.getItem_name());
            demandTable.setImg_url(demand.getImg_url());
            Set<Student> student = demand.getStudents();

            Iterator iterator = student.iterator();
            while(iterator.hasNext())
            {
                Student student1 = (Student) iterator.next();
                demandTable.setUsername(student1.getUsername());
                demandTable.setRollno(student1.getRollnumber());
                demandTable.setMobilenumber(student1.getMobilenumber());
                demandTable.setRoomnumber(student1.getRoomnumber());
            }
            demandTables.add(demandTable);
        }
        return demandTables;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
