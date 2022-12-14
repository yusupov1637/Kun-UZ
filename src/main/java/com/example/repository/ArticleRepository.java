package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfo;
import com.example.mapper.CArticleGetLast8;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible=false where id=?1")
    void deleteArticleById(String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set title=?1, description=?2, content=?3, " +
            "image.id=?4, region.id=?5, category.id=?6," +
            "status=?7 where id = ?8")
    void update(String title, String description, String content,
                String imageId, Integer regionId, Integer categoryId, String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status=?1, publishedDate = LOCAL DATETIME, publisherId=?2 where id=?3")
    int changeStatus(String status, Integer publisherId, String id);

    @Query("from ArticleEntity where status = ?1 order by createdDate desc ")
    List<ArticleEntity> getLastFive(String status);

    @Query("from ArticleEntity where status = ?1 order by createdDate desc ")
    List<ArticleEntity> getLastThree(String status);

    @Query("SELECT new com.example.mapper.CArticleGetLast8(a.id, a.title, a.description, a.image.id, a.publishedDate) FROM ArticleEntity as a where a.id not in (?1)")
    List<CArticleGetLast8> getLastEight(List<String> idList);

    @Query(value = "SELECT a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate FROM article as a where a.status='PUBLISHED' AND a.id not in (?1) order by created_date limit 8", nativeQuery = true)
    List<ArticleShortInfo> getLastEight1(List<String> idList);

    @Query(value = "SELECT a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate FROM article as a where a.status='PUBLISHED' AND a.id not in (?1) order by a.created_date desc limit 4", nativeQuery = true)
    List<ArticleShortInfo> getLastFour(String id);

    @Query(value = "Select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate from article as a where a.status='PUBLISHED' order by a.view_count desc limit 4", nativeQuery = true)
    List<ArticleShortInfo> getMostReads();

    @Query(value = "Select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate from article as a where a.status='PUBLISHED' and a.region_id=?1 order by created_date desc limit 5", nativeQuery = true)
    List<ArticleShortInfo> getLastFiveByRegion(Integer regionId);
//
//    @Query(value = "Select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate from article as a where a.status='PUBLISHED' and a.region_id=?1 order by created_date desc limit 5", nativeQuery = true)
//    Page<ArticleShortInfo> getPaginationByRegion(Pageable paging, Integer id);
    Page<ArticleShortInfo> findAllByRegionId(Pageable paging, Integer id);

    @Query(value = "Select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate from article as a where a.status='PUBLISHED' and a.category_id=?1 order by created_date desc limit 5", nativeQuery = true)
    List<ArticleShortInfo> getLastFiveByCategory(Integer categoryId);
    @Transactional
    @Modifying
    @Query(value = "update article  set view_count=view_count+1 where id=:articleId", nativeQuery = true)
    void increaseViewCount(@Param("articleId") String articleId);
    @Transactional
    @Modifying
    @Query(value = "update article  set shared_count=shared_count+1 where id=:articleId", nativeQuery = true)
    void shareCount(@Param("articleId") String articleId);

}
