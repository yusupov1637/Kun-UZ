package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<?> createRegion(HttpServletRequest request,
                                          @RequestBody RegionDTO regionDTO) {
        Integer adminId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        RegionDTO response = regionService.create(regionDTO, adminId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{regionId}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer regionId,
                                          @RequestBody RegionDTO regionDTO,
                                          HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        RegionDTO response = regionService.update(regionId, regionDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete/{regionId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer regionId,
                                        HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        String response = regionService.deleteById(regionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRegionList(HttpServletRequest request,
                                           @RequestParam Integer page,
                                           @RequestParam Integer size) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Page<RegionDTO> response = regionService.getList(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/language")
    public ResponseEntity<?> getByLanguage(@RequestParam Language lang) {
        List<RegionDTO> response = regionService.getByLanguage(lang);
        return ResponseEntity.ok(response);
    }

}
