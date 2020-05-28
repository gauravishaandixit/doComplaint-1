package com.doComplaint.rents;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RentServiceTest {

    @Autowired
    private RentService rentService;

    @MockBean
    private RentRepository rentRepository;

    @Test
    public void addRentTest(){
        Long id = 10L;
        Rent rent = new Rent();
        rent.setId(id);
        rent.setItem_name("book");
        rent.setShortDesc("novel");
        rent.setPrice("1000");

        when(rentRepository.save(rent)).thenReturn(rent);
        assertEquals(id,rentService.addRent(rent));
    }

    @Test
    public void updateRentTest(){
        Long id = 10L;
        Rent rent = new Rent();
        rent.setId(id);
        rent.setItem_name("book");
        rent.setShortDesc("novel");
        rent.setPrice("1000");

        when(rentRepository.save(rent)).thenReturn(rent);
        assertEquals(id,rentService.addRent(rent));
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Rent rent =new Rent();
        rent.setId(id);

        when(rentRepository.findById(id)).thenReturn(java.util.Optional.of(rent));

        assertEquals(rent,rentService.findById(id));
    }

    @Test
    public  void findAllTest(){
        Rent rent = new Rent();
        rent.setId(1L);
        Rent rent1 = new Rent();
        rent1.setId(2L);

        when(rentRepository.findAll()).thenReturn(Stream.of(rent,rent1).collect(Collectors.toList()));
        assertEquals(2,rentService.findAll().size());
    }
}