package com.example.springsecuritydemo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticationRestControllerV1 {

    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request){
        return null;
    }

}
