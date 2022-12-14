package com.example.service;

import com.example.dto.auth.AuthLoginDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exceptions.*;
import com.example.repository.EmailRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfileService profileService;

    public AuthResponseDTO login(AuthLoginDTO dto) {
        ProfileEntity profile = profileRepository.findByPhoneAndPassword(dto.getPhone(), MD5Util.encode(dto.getPassword()));

        if (profile == null) {
            throw new PasswordOrPhoneWrongException("Password or Phone is wrong");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppForbiddenException("No access");
        }

        return toResponseDTO(profile);
    }

    public String register(RegistrationDTO dto) {
        checkParameters(dto);

        ProfileEntity profile = profileRepository.findByPhone(dto.getPhone());

        if (profile != null) {
            if (!profile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                throw new PhonoAlreadyExistsException("Phone already exist");
            } else {
                profileRepository.delete(profile);
            }
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());

        String password = MD5Util.encode(dto.getPassword());
        entity.setPassword(password);

        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        int count = 0;

//        List<EmailHistory> oneEmailList = new ArrayList<>();
//        List<EmailHistory> emails = emailRepository.findAll();
//        for (int i = 1; i< emails.size(); i++) {
//            if(emails.get(i).getEmail().equals(dto.getEmail())){
//                count++;
//                oneEmailList.add(emails.get(i));
//            }
//        }
//
//        if(count>=5){
//
//        }


        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center\">Complete Registration</h1>");
        String link = String.format("<a href=\"http://192.168.0.106:8080/authorization/verification/email/%s\"> Click there </a>", JwtUtil.encode(entity.getId()));
        sb.append(link);
        emailService.sendEmailMime(dto.getEmail(), "Complete registration", sb.toString());


        if (entity.getId() == 0) {
            throw new ProfileCreateException("Something went wrong");
        }
        return "Successfully registered";
    }

    private AuthResponseDTO toResponseDTO(ProfileEntity profile) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setName(profile.getName());
        authResponseDTO.setSurname(profile.getSurname());
        authResponseDTO.setRole(profile.getRole());
        authResponseDTO.setToken(JwtUtil.encode(profile.getId(), profile.getRole()));
        return authResponseDTO;
    }

    public void checkParameters(RegistrationDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank() || dto.getName().trim().length() < 3) {
            throw new AppBadRequestException("Name is wrong");
        }

        if (dto.getSurname() == null || dto.getSurname().isBlank() || dto.getSurname().trim().length() < 3) {
            throw new AppBadRequestException("Surname is wrong");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank() || dto.getPassword().trim().length() < 3) {
            throw new AppBadRequestException("Password is wrong");
        }

        if (dto.getPhone() == null || dto.getPhone().isBlank() || dto.getPhone().trim().length() < 3) {
            throw new AppBadRequestException("Phone is wrong");
        }
    }

    public String verification(String jwt) {
        Integer id;
        try {
            id = JwtUtil.decodeForEmailVerification(jwt);
        } catch (JwtException e) {
            return "Verification failed";
        }

        ProfileEntity exists = profileService.get(id);
        if (!exists.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
            return "Verification failed";
        }

        exists.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(exists);

        return "Verification success";
    }
}
