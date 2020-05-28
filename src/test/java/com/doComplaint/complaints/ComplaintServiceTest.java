package com.doComplaint.complaints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComplaintServiceTest {

    @Autowired
    private ComplaintService complaintService;

    @MockBean
    private ComplaintRepository complaintRepository;

    @Test
    public void addComplaintTest(){
        Long id = 1L;
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setStatus("unresolved");
        complaint.setIssue("room cleaning");

        when(complaintRepository.save(complaint)).thenReturn(complaint);
        assertEquals(true,complaintService.addComplaint(complaint));
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setStatus("unresolved");
        complaint.setIssue("room cleaning");

        when(complaintRepository.findById(id)).thenReturn(java.util.Optional.of(complaint));

        assertEquals(complaint,complaintService.findById(id));
    }

    @Test
    public  void findAllTest(){
        Complaint complaint = new Complaint();
        complaint.setId(1L);
        Complaint complaint1 = new Complaint();
        complaint1.setId(2L);

        when(complaintRepository.findAll()).thenReturn(Stream.of(complaint,complaint1).collect(Collectors.toList()));
        assertEquals(2,complaintService.findAll().size());
    }


    @Test
    public  void findResolvedTest(){
        Complaint complaint = new Complaint();
        complaint.setId(1L);
        complaint.setStatus("unresolved");
        Complaint complaint1 = new Complaint();
        complaint1.setId(2L);
        complaint1.setStatus("unresolved");

        when(complaintRepository.findComplaintByStatusStartingWith("un")).thenReturn(Stream.of(complaint,complaint1).collect(Collectors.toList()));
        assertEquals(2,complaintService.findResolved().size());
    }


}