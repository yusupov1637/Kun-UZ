package com.example.controller;

import com.example.dto.attach.AttachDTO;
import com.example.enums.ProfileRole;
import com.example.service.AttachService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachControllerNew {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file) {
        AttachDTO response = attachService.saveToSystem(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/load/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] loadImage(@PathVariable String fileName) {
        byte[] response = attachService.loadImage(fileName);
        return response;
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] openGeneral(@PathVariable String fileName) {
        byte[] response = attachService.openGeneral(fileName);
        return response;
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> download(@PathVariable String fileName) {
        Resource response = attachService.download(fileName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getList(HttpServletRequest request,
                                     @RequestParam Integer page,
                                     @RequestParam Integer size){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Page<AttachDTO> response= attachService.getList(page, size);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
                                        @PathVariable String id){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Boolean response = attachService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
