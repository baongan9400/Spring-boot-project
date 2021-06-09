package com.ngandang.intern.model.dto;

import com.ngandang.intern.entity.Role;

import java.util.HashSet;
import java.util.Set;

public class UserInformDTO extends UserBaseDTO {
    private Integer id;

    private Set<Role> roles = new HashSet<>();

    public UserInformDTO() {
        super();
    }

    public UserInformDTO(Integer id,String username, String email, String phone,Set<Role> roles ) {
        super(username,email,phone);
        this.id = id;
        this.roles = roles;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
