package com.ngandang.intern.controller;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.entity.Order;
import com.ngandang.intern.model.dto.Mapper;
import com.ngandang.intern.model.request.RequestLogin;
import com.ngandang.intern.model.request.RequestSignup;
import com.ngandang.intern.model.response.JwtResponse;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.entity.User;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.reporitory.RoleRepository;
import com.ngandang.intern.reporitory.UserRepository;
import com.ngandang.intern.security.jwt.JWTUtils;
import com.ngandang.intern.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/signup")
    @ResponseBody
    public ResponseTransfer signup(@Valid @RequestPart("user") RequestSignup requestSignup,
                                   @RequestPart(value = "avatar",  required = false) MultipartFile file)
            throws IOException {
        if(userRepository.existsByUsername(requestSignup.getUsername())){
            throw  new ResourceNotFoundException("Sign up fail. Username is already taken!");
        }
        if(userRepository.existsByEmail(requestSignup.getEmail())){
            throw  new ResourceNotFoundException("Sign up fail. Email is already taken!");
        }
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
        this.userRepository.save(user);
        return new ResponseTransfer("Sign up successfully", Mapper.toUserDTO(user));
    }
    @PostMapping(path="/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestLogin requestLogin)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJWToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping(value = "/{id}",  produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatar(@PathVariable Integer id) {
        return userRepository.findUserById(id).map(user ->
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getUsername() + "\"")
                        .body(user.getAvatar())).orElseThrow(() -> new ResourceNotFoundException("URL not found."));
    }

}
