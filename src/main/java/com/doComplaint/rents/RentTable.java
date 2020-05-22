package com.doComplaint.rents;

import com.doComplaint.student.Student;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class RentTable {
    private Long id;
    private Timestamp timestamp;
    private String title;
    private String shortDesc;
    private String img_url;
    private String desc;
    private String rent;
    private String username;
    private String rollno;
    private String roomnumber;
    private String mobilenumber;

    List<RentTable> changeStructure(List<Rent> rents)
    {
        List<RentTable> rentTables = new ArrayList<RentTable>();
        for(Rent rent: rents)
        {
            RentTable rentTable = new RentTable();

            rentTable.setId(rent.getId());
            rentTable.setTimestamp(rent.getTimestamp());
            rentTable.setShortDesc(rent.getShortDesc());
            rentTable.setTitle(rent.getItem_name());
            rentTable.setDesc(rent.getDescription());
            rentTable.setImg_url(rent.getImg_url());
            rentTable.setRent(rent.getPrice());
            Set<Student> student = rent.getStudents();

            Iterator iterator = student.iterator();
            while(iterator.hasNext())
            {
                Student student1 = (Student) iterator.next();
                rentTable.setUsername(student1.getUsername());
                rentTable.setRollno(student1.getRollnumber());
                rentTable.setMobilenumber(student1.getMobilenumber());
                rentTable.setRoomnumber(student1.getRoomnumber());
            }
            rentTables.add(rentTable);
        }
        return rentTables;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
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
