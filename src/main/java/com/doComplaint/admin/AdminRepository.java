package com.doComplaint.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findAdminByUsernameAndPassword(String username, String password);
    Admin findAdminByUsername(String username);
    Admin findAdminByMobile(String mobile);

}
