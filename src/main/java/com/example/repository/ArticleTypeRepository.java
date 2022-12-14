package com.example.repository;


import com.example.entity.ArticleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity as at set at.key=?1, at.nameUz=?2, at.nameRus=?3, at.nameEng=?4 where at.id=?5")
    void updateArticleType(String key, String nameUz, String nameRus, String nameEng, Integer articleId);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity as at set at.visible=false where at.id=?1")
    void deleteArticleTypeVisible(Integer articleId);
}
