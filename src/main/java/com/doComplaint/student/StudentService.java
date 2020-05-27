package com.doComplaint.student;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.complaints.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ComplaintService complaintService;

    private JavaMailSender javaMailSender;
    private Path fileStorageLocation = Paths.get("/zprojectimages/profile/");

    @Autowired
    public StudentService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

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

    public void sendEmail(Student owner,Student requester){
        String email = requester.getEmail();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("demand accepted");

        String mailContent = "your request is accepted, details of owner is followed \n";
        mailContent += "username: " + owner.getUsername() + "\n";
        mailContent += "roomno: " + owner.getRoomnumber() + "\n";
        mailContent += "email: " + owner.getEmail() + "\n";
        mailContent += "mobileno: " + owner.getMobilenumber() + "\n";

        mailMessage.setText(mailContent);

        System.out.println(mailContent);
        javaMailSender.send(mailMessage);
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {

        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            System.out.println("File not found " + fileName);
            return null;
        }
    }
}
