package com.doComplaint.student;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, String> {

    Student findStudentByUsernameAndPassword(String username, String password);
    Student findStudentByUsername(String username);
    Student findStudentByRollnumber(String rollnumber);
}
