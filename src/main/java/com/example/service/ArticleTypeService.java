package com.example.service;

import com.example.dto.article.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.enums.Language;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(Integer adminId, ArticleTypeDTO articleTypeDTO) {
        articleTypeDTO.setCreatorId(adminId);
        ArticleTypeEntity articleType = toEntity(articleTypeDTO);

        articleTypeRepository.save(articleType);
        articleTypeDTO.setId(articleType.getId());
        return articleTypeDTO;
    }

    public ArticleTypeDTO update(Integer articleId, ArticleTypeDTO articleType) {
        articleTypeRepository.findById(articleId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        articleTypeRepository.updateArticleType(articleType.getKey(), articleType.getNameUz(),
                                                articleType.getNameRus(), articleType.getNameEng(), articleId);

        articleType.setId(articleId);
        return articleType;
    }

    public String deleteById(Integer articleId) {
        articleTypeRepository.findById(articleId).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });

        articleTypeRepository.deleteArticleTypeVisible(articleId);
        return "Deleted";
    }

    public Page<ArticleTypeDTO> getListPagination(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page-1, size);

        Page<ArticleTypeEntity> entityPage = articleTypeRepository.findAll(paging);

        List<ArticleTypeEntity> entities = entityPage.getContent();

        List<ArticleTypeDTO> dtoList = new ArrayList<>();

        for (ArticleTypeEntity entity : entities) {
            ArticleTypeDTO dto = toDTO(entity);
            dtoList.add(dto);
        }
        Page<ArticleTypeDTO> response = new PageImpl<>(dtoList, paging, entityPage.getTotalElements());
        return response;
    }

    public List<ArticleTypeDTO> getByLanguage(Language lang) {
        List<ArticleTypeEntity> entityList = articleTypeRepository.findAll();

        List<ArticleTypeDTO> dtoList = new ArrayList<>();

        for (ArticleTypeEntity articleType : entityList) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setId(articleType.getId());
            dto.setKey(articleType.getKey());
            switch (lang){
                case UZBEK-> dto.setNameUz(articleType.getNameUz());
                case RUSSIAN-> dto.setNameRus(articleType.getNameRus());
                case ENGLISH-> dto.setNameEng(articleType.getNameEng());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }





    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRus(entity.getNameRus());
        dto.setNameEng(entity.getNameEng());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    private ArticleTypeEntity toEntity(ArticleTypeDTO articleTypeDTO) {
        ArticleTypeEntity articleType = new ArticleTypeEntity();
        articleType.setNameUz(articleTypeDTO.getNameUz());
        articleType.setNameRus(articleTypeDTO.getNameRus());
        articleType.setNameEng(articleTypeDTO.getNameEng());
        articleType.setCreatorId(articleTypeDTO.getCreatorId());
        return articleType;
    }
}
