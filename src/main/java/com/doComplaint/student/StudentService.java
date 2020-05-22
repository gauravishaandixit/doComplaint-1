package com.doComplaint.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    boolean doesStudentExists(Student student)
    {
        Student student1 = studentRepository.findStudentByUsernameAndPassword(student.getUsername(), student.getPassword());
        return student1 == null ? false : true;
    }

    boolean addStudent(Student student)
    {
        Student student1 = studentRepository.findStudentByUsername(student.getUsername());
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
}
