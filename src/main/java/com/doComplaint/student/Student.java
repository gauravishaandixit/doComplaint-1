package com.doComplaint.student;

import com.doComplaint.complaints.Complaint;
import com.doComplaint.demand.Demand;
import com.doComplaint.rents.Rent;
import com.doComplaint.sells.Sell;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_table")
public class Student {

    @Id
    @Column(unique = true)
    private String rollnumber;
    private String username;
    private String imgUrl;
    private String password;
    private String roomnumber;
    private String mobilenumber;
    private String email;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "student_complaint",
            joinColumns = {@JoinColumn(name = "stud_roll")},
            inverseJoinColumns = {@JoinColumn(name = "comp_id")}
    )
    private Set<Complaint> complaints = new HashSet<>();



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "student_rent",
            joinColumns = {@JoinColumn(name = "stud_roll")},
            inverseJoinColumns = {@JoinColumn(name = "rent_id")}
    )
    private Set<Rent> rents = new HashSet<>();


    public Set<Rent> getRents() { return rents; }

    public void setRents(Set<Rent> rents) { this.rents = rents;   }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "student_sell",
            joinColumns = {@JoinColumn(name = "stud_roll")},
            inverseJoinColumns = {@JoinColumn(name = "sell_id")}
    )
    private Set<Sell> sells = new HashSet<>();


    public Set<Sell> getSells() {
        return sells;
    }

    public void setSells(Set<Sell> sells) {
        this.sells = sells;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "student_demand",
            joinColumns = {@JoinColumn(name = "stud_roll")},
            inverseJoinColumns = {@JoinColumn(name = "demand_id")}
    )
    private Set<Demand> demands = new HashSet<>();

    public Set<Demand> getDemands() {
        return demands;
    }

    public void setDemands(Set<Demand> demands) {
        this.demands = demands;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() { return imgUrl;    }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl;  }

    public Set<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        this.complaints = complaints;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
