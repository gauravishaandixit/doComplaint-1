package com.doComplaint.student;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, String> {

    Student findStudentByRollnumberAndPassword(String rollnumber, String password);
    Student findStudentByUsername(String username);
    Student findStudentByRollnumber(String rollnumber);
}
