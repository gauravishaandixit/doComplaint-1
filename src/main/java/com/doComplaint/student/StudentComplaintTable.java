package com.doComplaint.student;

import com.doComplaint.complaints.Complaint;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StudentComplaintTable {
    private Long id;
    private Timestamp timestamp;
    private String status;
    private String issue;


    List<StudentComplaintTable> changeStructure(List<Complaint> complaints)
    {
        List<StudentComplaintTable> studentComplaintTables = new ArrayList<>();
        for(Complaint complaint: complaints)
        {
            StudentComplaintTable studentComplaintTable = new StudentComplaintTable();

            studentComplaintTable.setId(complaint.getId());
            studentComplaintTable.setIssue(complaint.getIssue());
            studentComplaintTable.setTimestamp(complaint.getTimestamp());
            studentComplaintTable.setStatus(complaint.getStatus());

            studentComplaintTables.add(studentComplaintTable);

            System.out.println(studentComplaintTable);
        }
        return studentComplaintTables;
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

}
