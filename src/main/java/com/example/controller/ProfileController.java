package com.example.controller;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Tag(name = "ProfileController API",description = "Api list for ProfileEntity")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Operation(summary = "Create profile")
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> createProfile(HttpServletRequest request,
                                           @Valid @RequestBody ProfileDTO profileDTO) {
        Integer adminID = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        ProfileDTO profile = profileService.create(profileDTO, adminID);
        return ResponseEntity.ok(profile);
    }
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO filterDTO,@RequestParam int page,
                                    @RequestParam int size){
        profileService.filter(filterDTO,page,size);
        return ResponseEntity.ok().build();
    }
//    @PostMapping("/filter")
//    public ResponseEntity<?> update(@RequestBody ProfileFilterDTO filterDTO) {
//        profileService.filter(filterDTO);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/update/admin/{profileId}")
    public ResponseEntity<?> updateAdmin(HttpServletRequest request,
                                         @RequestBody ProfileDTO profileDTO,
                                         @PathVariable Integer profileId) {
        JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);

        ProfileDTO profile = profileService.updateAdmin(profileId, profileDTO);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody ProfileDTO profileDTO) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        ProfileDTO response = profileService.updateUser(pId, profileDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProfileDTO>> getProfileList(@RequestParam Integer page,
                                            @RequestParam Integer size,
                                            HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        Page<ProfileDTO> response = profileService.getProfilePagination(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/visible/{profileId}")
    public ResponseEntity<?> deleteProfileById(HttpServletRequest request,
                                               @PathVariable Integer profileId) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Boolean result = profileService.deleteProfileById(profileId);
        return ResponseEntity.ok(result);
    }
}
