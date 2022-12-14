package com.example.repository;


import com.example.controller.CategoryController;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity as r set r.key=?1, r.nameUz=?2, r.nameRu=?3, r.nameEng=?4 where r.id=?5")
    void update(String key, String nameUz, String nameRus, String nameEng, Integer regionId);


    Page<CategoryEntity> findAllByVisibleTrue(Pageable paging);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=?1")
    void deleteCategory(Integer regionId);


    List<CategoryEntity> findAllByVisibleTrue();

    CategoryEntity findAllByKey(String categoryKey);
}
