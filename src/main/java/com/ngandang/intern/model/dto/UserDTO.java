package com.ngandang.intern.model.dto;

import com.ngandang.intern.entity.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Integer id;

    private String username;

    private String email;

    private String phone;

    private Set<Role> roles = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(String username,String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
