package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO, Integer adminId) {
        categoryDTO.setCreatorId(adminId);
        CategoryEntity categoryEntity = toEntity(categoryDTO);

        categoryRepository.save(categoryEntity);
        categoryDTO.setId(categoryEntity.getId());
        return categoryDTO;
    }

    public CategoryDTO update(Integer categoryId, CategoryDTO categoryType) {
        categoryRepository.findById(categoryId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        categoryRepository.update(categoryType.getKey(), categoryType.getNameUz(),
                categoryType.getNameRu(), categoryType.getNameEng(), categoryId);

        categoryType.setId(categoryId);
        return categoryType;
    }

    public String deleteById(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        categoryRepository.deleteCategory(categoryId);
        return "Deleted";
    }

    public Page<CategoryDTO> getList(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page-1, size);

        Page<CategoryEntity> categoryPage = categoryRepository.findAllByVisibleTrue(paging);

        List<CategoryEntity> entities = categoryPage.getContent();

        List<CategoryDTO> dtoList = new ArrayList<>();

        for (CategoryEntity entity : entities) {
            CategoryDTO dto = toDTO(entity);
            dtoList.add(dto);
        }
        Page<CategoryDTO> response = new PageImpl<>(dtoList, paging, categoryPage.getTotalElements());
        return response;
    }

    public List<CategoryDTO> getByLanguage(Language lang) {
        List<CategoryEntity> entityList = categoryRepository.findAllByVisibleTrue();

        List<CategoryDTO> dtoList = new ArrayList<>();

        for (CategoryEntity categoryType : entityList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(categoryType.getId());
            dto.setKey(categoryType.getKey());
            switch (lang){
                case UZBEK -> dto.setNameUz(categoryType.getNameUz());
                case RUSSIAN -> dto.setNameRu(categoryType.getNameRu());
                case ENGLISH -> dto.setNameEng(categoryType.getNameEng());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEng(entity.getNameEng());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private CategoryEntity toEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNameEng(categoryDTO.getNameEng());
        categoryEntity.setNameUz(categoryDTO.getNameUz());
        categoryEntity.setNameRu(categoryDTO.getNameRu());
        categoryEntity.setCreatorId(categoryDTO.getCreatorId());
        return categoryEntity;
    }

    public CategoryEntity get(Integer categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ItemNotFoundException("Category not found");
        });
    }

    public CategoryDTO getById(Integer categoryId, Language language) {
        CategoryEntity category = get(categoryId);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setKey(category.getKey());

        switch (language){
            case UZBEK -> categoryDTO.setName(category.getNameUz());
            case RUSSIAN -> categoryDTO.setName(category.getNameRu());
            case ENGLISH -> categoryDTO.setName(category.getNameEng());
        }

        return categoryDTO;
    }

    public CategoryEntity getByKey(String categoryKey) {
        CategoryEntity categoryEntity = categoryRepository.findAllByKey(categoryKey);
        return categoryEntity;
    }
}
