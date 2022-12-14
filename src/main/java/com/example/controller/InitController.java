package com.example.controller;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exceptions.ItemAlreadyExistsException;
import com.example.repository.ProfileRepository;
import com.example.service.AttachService;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/init")
public class InitController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AttachService attachService;

    @GetMapping("/data")
    public String adminCreate(){
        Optional<ProfileEntity> optional = profileRepository.findAllByRole(ProfileRole.ADMIN);
        if(!optional.isEmpty())
            throw new ItemAlreadyExistsException("admin already created");

        ProfileEntity entity = new ProfileEntity();
        entity.setName("Muhammadsodiq");
        entity.setSurname("Nabijonov");
        entity.setPhone("3433");
        entity.setPassword(MD5Util.encode("3433"));
        entity.setEmail("muhammadsodiqnabijonov2502@gmail.com");
        entity.setPhoto(attachService.get("85fc5b21-3aa0-496c-bd36-216655eac5d9"));
        entity.setRole(ProfileRole.ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Admin added";
    }
}
