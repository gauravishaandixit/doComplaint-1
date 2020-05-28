package com.doComplaint.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminRepository adminRepository;

    @Test
    public void doeStudentExistTest(){
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");


        when(adminRepository.findAdminByUsernameAndPassword("admin","admin")).thenReturn(admin);
        assertEquals(true,adminService.doesAdminExists(admin));

    }

    @Test
    public void addAdminTest(){
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");

        when(adminRepository.save(admin)).thenReturn(admin);
        assertEquals(false,adminService.addAdmin(admin));
    }


}