package com.example.controller;

import com.example.dto.EmailDTO;
import com.example.entity.EmailHistory;
import com.example.enums.ProfileRole;
import com.example.service.EmailService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public ResponseEntity<?> getHistoryByEmail(HttpServletRequest request,
                                               @RequestParam String email) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        List<EmailDTO> response = emailService.getHistoryByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date")
    public ResponseEntity<?> getHistoryByGivenDate(HttpServletRequest request,
                                                   @RequestParam String date) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        List<EmailDTO> response = emailService.getHistoryByGivenDate(date);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/pagination")
    public ResponseEntity<?> getEmailHistoryPagination(HttpServletRequest request,
                                                       @RequestParam Integer page,
                                                       @RequestParam Integer size){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Page<EmailDTO> response = emailService.getEmailHistoryPagination(page, size);
        return ResponseEntity.ok(response);
    }
}
