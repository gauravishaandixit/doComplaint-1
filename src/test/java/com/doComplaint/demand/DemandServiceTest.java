package com.doComplaint.demand;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DemandServiceTest {
    @Autowired
    private DemandService demandService;

    @MockBean
    private DemandRepository demandRepository;

    @Test
    public void addDemandTest(){
        Long id = 10L;
        Demand demand = new Demand();
        demand.setId(id);
        demand.setItem_name("book");
        demand.setShortDesc("novel");

        when(demandRepository.save(demand)).thenReturn(demand);
        assertEquals(id,demandService.addDemand(demand));
    }

    @Test
    public void updateDemandTest(){
        Long id = 10L;
        Demand demand = new Demand();
        demand.setId(id);
        demand.setItem_name("book");
        demand.setShortDesc("novel");

        when(demandRepository.save(demand)).thenReturn(demand);
        assertEquals(id,demandService.addDemand(demand));
    }

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Demand demand =new Demand();
        demand.setId(id);

        when(demandRepository.findById(id)).thenReturn(java.util.Optional.of(demand));

        assertEquals(demand,demandService.findById(id));
    }

    @Test
    public  void findAllTest(){
        Demand demand = new Demand();
        demand.setId(1L);
        Demand demand1 = new Demand();
        demand1.setId(2L);

        when(demandRepository.findAll()).thenReturn(Stream.of(demand,demand1).collect(Collectors.toList()));
        assertEquals(2,demandService.findAll().size());
    }
}