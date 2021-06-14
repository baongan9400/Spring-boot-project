package com.ngandang.intern.controller;

import com.ngandang.intern.model.request.RequestLogin;
import com.ngandang.intern.model.request.RequestSignup;
import com.ngandang.intern.model.response.JwtResponse;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.security.jwt.JWTUtils;
import com.ngandang.intern.service.UserDetailsImpl;
import com.ngandang.intern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping(path="/signup")
    @ResponseBody
    public ResponseTransfer signup(@Valid @RequestPart("user") RequestSignup requestSignup,
                                   @RequestPart(value = "avatar",  required = false) MultipartFile file)
            throws IOException {
        if (userService.checkExistUser(requestSignup)){
            return new ResponseTransfer("Sign up successfully",
                    userService.save(requestSignup,file));
        }
        return new ResponseTransfer("Sign up fail", null);
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
                roles,
                userDetails.getUrl()));
    }

    @GetMapping(value = "/{id}",  produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> avatar(@PathVariable Integer id) {
        return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"vechai" + id + "\"")
                        .body(userService.getAvatar(id));
    }

}
