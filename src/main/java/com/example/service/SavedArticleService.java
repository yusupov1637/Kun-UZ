package com.example.service;

import com.example.dto.article.SavedArticleDTO;
import com.example.entity.SavedArticleEntity;
import com.example.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedArticleService {

    @Autowired
    private SavedArticleRepository savedArticleRepository;

    @Autowired
    private ArticleService articleService;

    public SavedArticleDTO create(SavedArticleDTO dto) {
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(dto.getProfileId());
        savedArticleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public String delete(SavedArticleDTO dto) {
        int delete = savedArticleRepository.delete(dto.getArticleId(), dto.getProfileId());
        if(delete==0){
            throw new RuntimeException("ERROR");
        }
        return "Deleted";
    }

    public List<SavedArticleDTO> getList() {
        List<SavedArticleEntity> getList = savedArticleRepository.findAll();


        List<SavedArticleDTO> dtoList = new ArrayList<>();

        for (SavedArticleEntity entity : getList) {
            SavedArticleDTO savedArticleDTO = new SavedArticleDTO();
            savedArticleDTO.setId(entity.getId());
            savedArticleDTO.setArticle(articleService.getArticleShortInfo(entity.getArticleId()));
            dtoList.add(savedArticleDTO);
        }
        return dtoList;
    }
}
