package com.ngandang.intern.controller;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.payload.RequestLogin;
import com.ngandang.intern.payload.RequestSignup;
import com.ngandang.intern.payload.ResponseTransfer;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.entity.User;
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
            return new ResponseTransfer("Username is already taken!","Sign up fail",null);
        }
        if(userRepository.existsByEmail(requestSignup.getEmail())){
            return new ResponseTransfer("Email is already taken!","Sign up fail",null);
        }
        Set<String> strRoles = requestSignup.getRole();
        Set<Role> roles = new HashSet<>();
        // Create new user's account
        User user = new User(requestSignup.getUsername(),
                requestSignup.getPassword(),
                requestSignup.getEmail(),
                requestSignup.getPhone());
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_SELLER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
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
        }

        user.setRoles(roles);
        this.userRepository.save(user);
        return new ResponseTransfer(null,"Sign up successfully",user);
    }
    @PostMapping(path="/login")
    @ResponseBody
    public ResponseTransfer login(@Valid @RequestBody RequestLogin userLogin)
    {
        User user = userRepository.findByUsernameAndPassword(userLogin.getUsername(),userLogin.getPassword());
        if (user !=null){
            return new ResponseTransfer(null,"Sign in successfully", user);
        } else
            return new ResponseTransfer("Wrong username!","Sign in fail",null);
    }

}
