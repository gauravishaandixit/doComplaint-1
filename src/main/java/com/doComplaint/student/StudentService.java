package com.doComplaint.student;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.complaints.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ComplaintService complaintService;

    boolean doesStudentExists(Student student)
    {
        Student student1 = studentRepository.findStudentByRollnumberAndPassword(student.getRollnumber(), student.getPassword());
        return student1 == null ? false : true;
    }

    boolean addStudent(Student student)
    {
        Student student1 = studentRepository.findStudentByRollnumber(student.getRollnumber());
        if(student1 == null)
        {
            studentRepository.save(student);
            return false;
        }
        else
            return true;
    }

    public Student findStudentByRollnumber(String rollnumber)
    {
        return studentRepository.findStudentByRollnumber(rollnumber);
    }

    public Student findStudentByUsername(String username)
    {
        return studentRepository.findStudentByUsername(username);
    }

    public String updateStatus(Long id)
    {

        Complaint complaint = complaintService.findById(id);

        if(complaint == null)
            return "Complaint Not Found";

        if(complaint.getStatus().equals("resolved"))
            complaint.setStatus("unresolved");
        else
            complaint.setStatus("resolved");
        complaintService.addComplaint(complaint);
        return complaint.getStatus();
    }

    public String updateURL(Student student){
        studentRepository.save(student);
        return student.getRollnumber();
    }
}
