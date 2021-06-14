package com.ngandang.intern.service;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.entity.User;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.model.dto.Mapper;
import com.ngandang.intern.model.dto.UserInformDTO;
import com.ngandang.intern.model.request.RequestSignup;
import com.ngandang.intern.reporitory.RoleRepository;
import com.ngandang.intern.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;
    public Boolean checkExistUser(RequestSignup requestSignup){
        if(userRepository.existsByUsername(requestSignup.getUsername())){
            throw  new ResourceNotFoundException("Sign up fail. Username is already taken!");
        }
        if(userRepository.existsByEmail(requestSignup.getEmail())){
            throw  new ResourceNotFoundException("Sign up fail. Email is already taken!");
        }
        return true;
    }
    public UserInformDTO save (RequestSignup requestSignup, MultipartFile file) throws IOException {
        Set<String> strRoles = requestSignup.getRole();
        Set<Role> roles = new HashSet<>();

        User user = new User(requestSignup.getUsername(),
                encoder.encode(requestSignup.getPassword()),
                requestSignup.getEmail(),
                requestSignup.getPhone());

        strRoles.forEach(role -> {
            if ("buyer".equals(role)) {
                Role modRole = roleRepository.findByName(ERole.ROLE_BUYER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_SELLER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        });

        user.setRoles(roles);
        user.setAvatar(file.getBytes());
        return Mapper.toUserDTO(this.userRepository.save(user));
    }
    public byte[] getAvatar (Integer id){
        return userRepository.findUserById(id).map(User::getAvatar)
                .orElseThrow(() -> new ResourceNotFoundException("URL not found."));
    }
}
