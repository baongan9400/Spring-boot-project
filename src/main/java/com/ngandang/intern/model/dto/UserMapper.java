package com.ngandang.intern.model.dto;

import com.ngandang.intern.entity.User;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO tmp = new UserDTO();
        tmp.setId(user.getId());
        tmp.setUsername(user.getUsername());
        tmp.setPhone(user.getPhone());
        tmp.setEmail(user.getEmail());
        tmp.setRoles(user.getRoles());

        return tmp;
    }
}