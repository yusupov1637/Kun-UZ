package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
import com.example.exceptions.ItemNotFoundException;
import com.example.mapper.ArticleShortInfo;
import com.example.mapper.CArticleGetLast8;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProfileService profileService;


    public ArticleDTO create(Integer adminId, ArticleDTO articleDTO) {
        articleDTO.setCreatedDate(LocalDateTime.now());
        articleDTO.setModeratorId(adminId);
        articleDTO.setStatus(ArticleStatus.NOT_PUBLISHED);

        ArticleEntity article = toEntity(articleDTO);
        articleRepository.save(article);

        articleDTO.setId(article.getId());
        return articleDTO;
    }
    public void increaseViewCount(String articleId){
      articleRepository.increaseViewCount(articleId);
    }

    public void shareCount(String articleId){
      articleRepository.shareCount(articleId);
    }

    private ArticleEntity toEntity(ArticleDTO articleDTO) {
        ArticleEntity article = new ArticleEntity();
        article.setTitle(articleDTO.getTitle());
        article.setDescription(articleDTO.getDescription());
        article.setContent(articleDTO.getContent());
        article.setSharedCount(articleDTO.getSharedCount());
        article.setImage(null);
        article.setRegion(regionService.get(articleDTO.getRegionId()));
        article.setCategory(categoryService.get(articleDTO.getCategoryId()));
        article.setModerator(profileService.get(articleDTO.getModeratorId()));
        article.setCreatedDate(articleDTO.getCreatedDate());
        article.setStatus(articleDTO.getStatus());
        return article;
    }

    public Boolean deleteById(String id) {
        ArticleEntity entity = get(id);

        articleRepository.deleteArticleById(id);
        return true;
    }

    private ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Article Not found");
        });
    }

    public ArticleDTO updateById(String id, ArticleDTO articleDTO) {
        get(id);

        articleRepository.update(articleDTO.getTitle(), articleDTO.getDescription(), articleDTO.getContent(),
                                 articleDTO.getImageId(), articleDTO.getRegionId(), articleDTO.getCategoryId(), id);


        articleDTO.setId(id);
        return articleDTO;
    }

    public String changeStatus(String id, ArticleStatus status, Integer publisherId) {
        int update = articleRepository.changeStatus(status.toString(),publisherId, id);

        if(update==0){
            throw new RuntimeException("ERROR");
        }
        return "Status updated";
    }

    public List<ArticleDTO> lastFive(ArticleStatus status) {
        List<ArticleEntity> getLastFive = articleRepository.getLastFive(status.toString());

        List<ArticleDTO> dtoList = new ArrayList<>();

        for (ArticleEntity article : getLastFive) {
            ArticleDTO articleDTO = toDTO(article);
            dtoList.add(articleDTO);
        }
        return dtoList;
    }

    private ArticleDTO toDTO(ArticleEntity article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setContent(article.getContent());
        articleDTO.setCreatedDate(article.getCreatedDate());
        articleDTO.setPublishedDate(article.getPublishedDate());
        return articleDTO;
    }

    public List<ArticleDTO> lastThree(ArticleStatus status) {
        List<ArticleEntity> getLastThree = articleRepository.getLastThree(status.toString());

        List<ArticleDTO> dtoList = new ArrayList<>();

        for (ArticleEntity article : getLastThree) {
            ArticleDTO articleDTO = toDTO(article);
            dtoList.add(articleDTO);
        }
        return dtoList;
    }

    public List<CArticleGetLast8> lastEight(List<String> idList) {
        List<CArticleGetLast8> entityList = articleRepository.getLastEight(idList);
        return entityList;
    }

    public List<ArticleShortInfo> lastEight1(List<String> idList) {
        List<ArticleShortInfo> entityList = articleRepository.getLastEight1(idList);
        return entityList;
    }

    public ArticleDTO getById(String id, Language language) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Article Not found");
        }

        ArticleEntity article = optional.get();

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setContent(article.getContent());
        articleDTO.setSharedCount(article.getSharedCount());
        articleDTO.setRegion(regionService.getById(article.getRegionId(), language));
        articleDTO.setCategory(categoryService.getById(article.getCategoryId(), language));
        articleDTO.setPublishedDate(article.getPublishedDate());
        articleDTO.setViewCount(article.getViewCount());
        return articleDTO;
    }

    public List<ArticleShortInfo> getLastFour(String id) {
        List<ArticleShortInfo> lastFour = articleRepository.getLastFour(id);
        return lastFour;
    }

    public List<ArticleShortInfo> getMostReads() {
        List<ArticleShortInfo> mostReads = articleRepository.getMostReads();
        return mostReads;
    }

    public List<ArticleShortInfo> getLastFiveByRegionKey(String regionKey) {
        RegionEntity region = regionService.getByKey(regionKey);

        List<ArticleShortInfo> list = articleRepository.getLastFiveByRegion(region.getId());
        return list;
    }

    public Page<ArticleShortInfo> getPaginationByRegionKey(String regionKey, Integer page, Integer size) {
        RegionEntity region = regionService.getByKey(regionKey);

        Pageable paging = PageRequest.of(page-1, size);

        Page<ArticleShortInfo> pageList = articleRepository.findAllByRegionId(paging, region.getId());
        List<ArticleShortInfo> list = pageList.getContent();

        Page<ArticleShortInfo> shortInfoPage = new PageImpl<>(list, paging, pageList.getTotalElements());
        return shortInfoPage;
    }

    public List<ArticleShortInfo> getLastFiveByCategoryKey(String categoryKey) {
        CategoryEntity category = categoryService.getByKey(categoryKey);

        List<ArticleShortInfo> list = articleRepository.getLastFiveByCategory(category.getId());
        return list;
    }

    public ArticleDTO getArticleShortInfo(String id){
        ArticleDTO articleDTO = new ArticleDTO();

        ArticleEntity article = get(id);
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setImageId(article.getImageId());
        return articleDTO;
    }
}
