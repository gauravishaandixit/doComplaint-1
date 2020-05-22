package com.doComplaint.complaints;

import com.doComplaint.student.Student;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    private String status;
    private  String issue;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "complaints", cascade = CascadeType.PERSIST)
    private Set<Student> students = new HashSet<>();

    public String getIssue() { return issue; }

    public void setIssue(String issue) { this.issue = issue; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

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

    public Set<Student> getStudent() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.getComplaints().add(this);
    }
}
