package com.doComplaint.admin;

import javax.persistence.*;

@Entity
@Table(name = "admin_table")
public class Admin {

    @Id
    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}