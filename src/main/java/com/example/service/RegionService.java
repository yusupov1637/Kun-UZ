package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionDTO regionDTO, Integer adminId) {
        regionDTO.setCreatorId(adminId);
        RegionEntity regionEntity = toEntity(regionDTO);

        regionRepository.save(regionEntity);
        regionDTO.setId(regionEntity.getId());
        return regionDTO;
    }

    public RegionDTO update(Integer regionId, RegionDTO regionType) {
        regionRepository.findById(regionId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        regionRepository.update(regionType.getKey(), regionType.getNameUz(),
                regionType.getNameRus(), regionType.getNameEng(), regionId);

        regionType.setId(regionId);
        return regionType;
    }

    public String deleteById(Integer regionId) {
        regionRepository.findById(regionId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        regionRepository.deleteRegion(regionId);
        return "Deleted";
    }

    public Page<RegionDTO> getList(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page-1, size);

        Page<RegionEntity> regionPage = regionRepository.findAllByVisibleTrue(paging);

        List<RegionEntity> entities = regionPage.getContent();

        List<RegionDTO> dtoList = new ArrayList<>();

        for (RegionEntity entity : entities) {
            RegionDTO dto = toDTO(entity);
            dtoList.add(dto);
        }
        Page<RegionDTO> response = new PageImpl<>(dtoList, paging, regionPage.getTotalElements());
        return response;
    }

    public List<RegionDTO> getByLanguage(Language lang) {
        List<RegionEntity> entityList = regionRepository.findAllByVisibleTrue();

        List<RegionDTO> dtoList = new ArrayList<>();

        for (RegionEntity regionType : entityList) {
            RegionDTO dto = new RegionDTO();
            dto.setId(regionType.getId());
            dto.setKey(regionType.getKey());
            switch (lang){
                case UZBEK -> dto.setNameUz(regionType.getNameUz());
                case RUSSIAN -> dto.setNameRus(regionType.getNameRus());
                case ENGLISH -> dto.setNameEng(regionType.getNameEng());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRus(entity.getNameRus());
        dto.setNameEng(entity.getNameEng());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private RegionEntity toEntity(RegionDTO regionDTO) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setNameEng(regionDTO.getNameEng());
        regionEntity.setNameUz(regionDTO.getNameUz());
        regionEntity.setNameRus(regionDTO.getNameRus());
        regionEntity.setCreatorId(regionDTO.getCreatorId());
        return regionEntity;
    }

    public RegionEntity get(Integer regionId){
        return  regionRepository.findById(regionId).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }

    public RegionDTO getById(Integer regionId, Language language) {
        RegionEntity region = get(regionId);

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setKey(region.getKey());

        switch (language){
            case UZBEK -> regionDTO.setName(region.getNameUz());
            case RUSSIAN -> regionDTO.setName(region.getNameRus());
            case ENGLISH -> regionDTO.setName(region.getNameEng());
        }

        return regionDTO;
    }

    public RegionEntity getByKey(String regionKey) {
        RegionEntity regionEntity = regionRepository.getByRegionKey(regionKey);
        return regionEntity;
    }
}
