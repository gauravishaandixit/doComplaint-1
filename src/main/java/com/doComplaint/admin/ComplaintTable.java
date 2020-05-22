package com.doComplaint.admin;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.student.Student;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ComplaintTable {
    private Long id;
    private Timestamp timestamp;
    private String status;
    private String issue;
    private String username;
    private String roomnumber;
    private String mobilenumber;

    List<ComplaintTable> changeStructure(List<Complaint> complaints)
    {
        List<ComplaintTable> complaintsTable = new ArrayList<ComplaintTable>();
        for(Complaint complaint: complaints)
        {
            ComplaintTable complaintTable = new ComplaintTable();

            complaintTable.setId(complaint.getId());

            complaintTable.setTimestamp(complaint.getTimestamp());

            complaintTable.setIssue(complaint.getIssue());

            complaintTable.setStatus(complaint.getStatus());

            Set<Student> student = complaint.getStudent();

            Iterator iterator = student.iterator();
            while(iterator.hasNext())
            {
                Student student1 = (Student) iterator.next();
                complaintTable.setUsername(student1.getUsername());
                complaintTable.setMobilenumber(student1.getMobilenumber());
                complaintTable.setRoomnumber(student1.getRoomnumber());
            }
            complaintsTable.add(complaintTable);
        }
        return complaintsTable;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
