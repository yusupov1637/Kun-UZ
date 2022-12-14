package com.example.controller;

import com.example.dto.auth.AuthLoginDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authorization")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Operation(summary = "Auth method",description = "method used for authorization")
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDTO dto){
        AuthResponseDTO authResponseDTO = authService.login(dto);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO registrationDTO){
        String response = authService.register(registrationDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/email/{jtwToken}")
    public ResponseEntity<?> emailVerification(@PathVariable("jtwToken") String jwt) {
        String response = authService.verification(jwt);
        return ResponseEntity.ok(response);
    }
}
