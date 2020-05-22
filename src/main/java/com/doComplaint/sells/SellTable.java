package com.doComplaint.sells;

import com.doComplaint.student.Student;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class SellTable {
    private Long id;
    private Timestamp timestamp;
    private String title;
    private String shortDesc;
    private String img_url;
    private String desc;
    private String Sell;
    private String username;
    private String rollno;
    private String roomnumber;
    private String mobilenumber;

    List<SellTable> changeStructure(List<Sell> Sells)
    {
        List<SellTable> sellTables = new ArrayList<SellTable>();
        for(Sell sell: Sells)
        {
            SellTable sellTable = new SellTable();

            sellTable.setId(sell.getId());
            sellTable.setTimestamp(sell.getTimestamp());
            sellTable.setShortDesc(sell.getShortDesc());
            sellTable.setTitle(sell.getItem_name());
            sellTable.setDesc(sell.getDescription());
            sellTable.setImg_url(sell.getImg_url());
            sellTable.setSell(sell.getPrice());
            Set<Student> student = sell.getStudents();

            Iterator iterator = student.iterator();
            while(iterator.hasNext())
            {
                Student student1 = (Student) iterator.next();
                sellTable.setUsername(student1.getUsername());
                sellTable.setRollno(student1.getRollnumber());
                sellTable.setMobilenumber(student1.getMobilenumber());
                sellTable.setRoomnumber(student1.getRoomnumber());
            }
            sellTables.add(sellTable);
        }
        return sellTables;
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

    public String getSell() {
        return Sell;
    }

    public void setSell(String sell) {
        Sell = sell;
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
