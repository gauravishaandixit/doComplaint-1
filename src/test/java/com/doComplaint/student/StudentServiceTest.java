package com.doComplaint.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void findStudentByRollnumberTest(){
        String rollnumber = "MT2019045";
        Student s = new Student();
        s.setEmail("a@b.com");
        s.setRollnumber("MT2019045");
        s.setMobilenumber("9986532145");
        s.setRoomnumber("485");
        s.setUsername("a b");

        when(studentRepository.findStudentByRollnumber(rollnumber)).thenReturn(s);

        //assertEquals(Student,studentService.findStudentByRollnumber(rollnumber));
        assertEquals(s,studentService.findStudentByRollnumber(rollnumber));
    }

    @Test
    public void addStudentTest(){
        Student student = new Student();
        student.setEmail("a@b.com");
        student.setRollnumber("MT2019045");
        student.setMobilenumber("9986532145");
        student.setRoomnumber("485");
        student.setUsername("a b");

        when(studentRepository.save(student)).thenReturn(student);

        assertEquals(true,studentService.addStudent(student));
    }

    @Test
    public void doeStudentExistTest(){
        Student student = new Student();
        student.setRollnumber("MT2019045");
        student.setPassword("password");


        when(studentRepository.findStudentByRollnumberAndPassword("MT2019045","password")).thenReturn(student);
        assertEquals(true,studentService.doesStudentExists(student));

    }

    @Test
    public void updateURLTest(){
        Student student = new Student();
        student.setRollnumber("MT2019045");
        student.setPassword("password");

        when(studentRepository.save(student)).thenReturn(student);

        assertEquals("MT2019045",studentService.updateURL(student));
    }
}