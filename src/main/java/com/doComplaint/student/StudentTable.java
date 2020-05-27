package com.doComplaint.student;

import java.util.ArrayList;
import java.util.List;

public class StudentTable {
    private String rollnumber;
    private String username;
    private String imgUrl;
    private String roomnumber;
    private String mobilenumber;

    StudentTable changeStructure(Student student){
        StudentTable studentTable = new StudentTable();

            studentTable.setRollnumber(student.getRollnumber());
            studentTable.setMobilenumber(student.getMobilenumber());
            studentTable.setImgUrl(student.getImgUrl());
            studentTable.setRoomnumber(student.getRoomnumber());
            studentTable.setUsername(student.getUsername());

        return  studentTable;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
