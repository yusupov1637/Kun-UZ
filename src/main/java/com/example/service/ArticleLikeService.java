package com.example.service;

import com.example.dto.article.ArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.enums.ArticleLikeStatus;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public void putReaction(ArticleLikeDTO dto) {
        ArticleLikeEntity exists = get(dto);

        if(exists!=null){
            update(dto);
            return;
        }

        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(dto.getProfileId());
        entity.setStatus(dto.getStatus());
        articleLikeRepository.save(entity);
    }

    public void update(ArticleLikeDTO dto) {
        ArticleLikeEntity entity = get(dto);

        if(entity.getStatus().equals(dto.getStatus())) {
            remove(dto);
            return;
        }else if(entity.getStatus().equals(ArticleLikeStatus.LIKE)){
            dto.setStatus(ArticleLikeStatus.DISLIKE);
        }else if(entity.getStatus().equals(ArticleLikeStatus.DISLIKE)){
            dto.setStatus(ArticleLikeStatus.LIKE);
        }
        entity.setStatus(dto.getStatus());
        articleLikeRepository.save(entity);
    }

    public ArticleLikeEntity get(ArticleLikeDTO dto){
        return articleLikeRepository.findByProfileIdAndArticleId(dto.getProfileId(), dto.getArticleId());
    }

    public void remove(ArticleLikeDTO dto) {
        articleLikeRepository.removeReaction(dto.getArticleId(), dto.getProfileId());
    }
}
