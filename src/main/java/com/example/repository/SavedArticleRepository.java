package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SavedArticleRepository extends JpaRepository<SavedArticleEntity, Integer> {

    @Transactional
    @Modifying
    @Query("delete from SavedArticleEntity as sa where sa.article.id=?1 and sa.profile.id=?2")
    int delete(String articleId, Integer profileId);
}
