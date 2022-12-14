package com.example.service;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exceptions.AppBadRequestException;
import com.example.exceptions.AppForbiddenException;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.repository.custom.ProfileCustomRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AttachService attachService;

    @Autowired
    private ProfileCustomRepository customRepository;


    public ProfileDTO create(ProfileDTO profileDTO, Integer adminId) {
        // profileDTO ->  {name, surname, phone, password, role}
//        checkParameters(profileDTO);

        ProfileEntity profile = toEntity(profileDTO);
        profile.setCreatorId(adminId);

        profileRepository.save(profile);
        profileDTO.setId(profile.getId());
        return profileDTO;
    }


    public Page<ProfileDTO> getProfilePagination(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ProfileEntity> entityPage = profileRepository.findAll(paging);

        List<ProfileEntity> entities = entityPage.getContent();
        List<ProfileDTO> profileDTOList = new ArrayList<>();

        for (ProfileEntity entity : entities) {
            ProfileDTO profileDTO = toDTO(entity);
            profileDTOList.add(profileDTO);
        }

        Page<ProfileDTO> dtoPage = new PageImpl<>(profileDTOList, paging, entityPage.getTotalElements());
        return dtoPage;
    }

    public Boolean deleteProfileById(Integer profileId) {
        profileRepository.findById(profileId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        profileRepository.deleteProfileVisibility(profileId);
        return true;
    }

    public ProfileDTO updateAdmin(Integer profileId, ProfileDTO profileDTO) {
        int update = profileRepository.updateProfileByAdmin(profileDTO.getName(), profileDTO.getSurname(), profileDTO.getVisible(),
                profileDTO.getRole(), profileDTO.getStatus(), profileId);

        if (update == 0) {
            throw new AppForbiddenException("Something went wrong");
        }

        profileDTO.setId(profileId);
        return profileDTO;
    }

    public ProfileDTO updateUser(Integer pId, ProfileDTO profileDTO) {
        profileRepository.updateUserById(profileDTO.getName(), profileDTO.getSurname(),
                MD5Util.encode(profileDTO.getPassword()), pId);

        return profileDTO;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setRole(entity.getRole());
        profileDTO.setVisible(entity.getVisible());
        return profileDTO;
    }

    private ProfileEntity toEntity(ProfileDTO profileDTO) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setEmail(profileDTO.getEmail());
        entity.setPassword(MD5Util.encode(profileDTO.getPassword()));
        entity.setRole(profileDTO.getRole());
        entity.setPhoto(attachService.get(profileDTO.getImageId()));

        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

    public void checkParameters(ProfileDTO dto) {
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

    public ProfileEntity get(Integer id) {
        ProfileEntity profile = profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found");
        });
        return profile;
    }
    public void filter(ProfileFilterDTO filterDTO,int page,int size) {
        List<ProfileEntity> profileEntityList = customRepository.filter(filterDTO,page,size);
        profileEntityList.forEach(entity -> {
            System.out.println(entity.getId());
            System.out.println(entity.getName());
            System.out.println(entity.getPhone());
            System.out.println(entity.getCreatedDate());
        });
    }
}
