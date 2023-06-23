package com.screenspot.authenticationservice.controller;

import com.screenspot.authenticationservice.model.JwtRequest;
import com.screenspot.authenticationservice.model.JwtResponse;
import com.screenspot.authenticationservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody @Valid JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
