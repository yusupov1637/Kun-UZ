package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, Integer> {

    ArticleLikeEntity findByProfileIdAndArticleId(Integer profileId, String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleLikeEntity as al where al.article.id=?1 and al.profile.id=?2")
    void removeReaction(String articleId, Integer profileId);
}
