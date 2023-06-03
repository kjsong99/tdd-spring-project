package com.song.tddproject.tddproject.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;

    public String password;

    public String name;

    public String address;

    public String phone;
    
    public String roles;
    
    public List<String> getRoles(){
        if (roles.length() > 0){
            return Arrays.asList(roles.split(","));

        }else{
            return new ArrayList<String>();
        }
    }

    @Builder
    public User(String username, String password, String name, String address, String phone, String roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.roles = roles;
    }
}
