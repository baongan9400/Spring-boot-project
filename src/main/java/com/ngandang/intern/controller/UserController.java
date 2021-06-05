package com.ngandang.intern.controller;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.model.dto.UserMapper;
import com.ngandang.intern.model.request.RequestLogin;
import com.ngandang.intern.model.request.RequestSignup;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.model.dto.UserDTO;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.entity.User;
import com.ngandang.intern.exception.LoginFailedException;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.reporitory.RoleRepository;
import com.ngandang.intern.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/signup")
    @ResponseBody
    public ResponseTransfer signup(@Valid @RequestBody RequestSignup requestSignup)
    {
        if(userRepository.existsByUsername(requestSignup.getUsername())){
            throw  new ResourceNotFoundException("Sign up fail. Username is already taken!");
        }
        if(userRepository.existsByEmail(requestSignup.getEmail())){
            throw  new ResourceNotFoundException("Sign up fail. Email is already taken!");
        }
        Set<String> strRoles = requestSignup.getRole();
        Set<Role> roles = new HashSet<>();

        User user = new User(requestSignup.getUsername(),
                requestSignup.getPassword(),
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
        this.userRepository.save(user);
        return new ResponseTransfer("Sign up successfully", UserMapper.toUserDTO(user));
    }
    @PostMapping(path="/login")
    @ResponseBody
    public ResponseTransfer login(@Valid @RequestBody RequestLogin userLogin)
    {
        User user = userRepository.findByUsernameAndPassword(userLogin.getUsername(),userLogin.getPassword());
        if (user !=null){
            return new ResponseTransfer("Sign in successfully",UserMapper.toUserDTO(user) );
        } else
            throw new LoginFailedException("The username or password was not correct");
    }

}
