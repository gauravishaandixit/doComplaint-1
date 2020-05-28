package com.doComplaint.sells;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SellServiceTest {
    @Autowired
    private SellService sellService;

    @MockBean
    private SellRepository sellRepository;

    @Test
    public void addSellTest(){
        Long id = 10L;
        Sell sell = new Sell();
        sell.setId(id);
        sell.setItem_name("book");
        sell.setShortDesc("novel");
        sell.setPrice("1000");

        when(sellRepository.save(sell)).thenReturn(sell);
        assertEquals(id,sellService.addSell(sell));
    }

    @Test
    public void updateSellTest(){
        Long id = 10L;
        Sell sell = new Sell();
        sell.setId(id);
        sell.setItem_name("book");
        sell.setShortDesc("novel");
        sell.setPrice("1000");

        when(sellRepository.save(sell)).thenReturn(sell);
        assertEquals(id,sellService.addSell(sell));
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Sell sell =new Sell();
        sell.setId(id);

        when(sellRepository.findById(id)).thenReturn(java.util.Optional.of(sell));

        assertEquals(sell,sellService.findById(id));
    }

    @Test
    public  void findAllTest(){
        Sell sell = new Sell();
        sell.setId(1L);
        Sell sell1 = new Sell();
        sell1.setId(2L);

        when(sellRepository.findAll()).thenReturn(Stream.of(sell,sell1).collect(Collectors.toList()));
        assertEquals(2,sellService.findAll().size());
    }
}