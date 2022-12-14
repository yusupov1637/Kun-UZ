package com.example.repository;

import com.example.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {


    @Transactional
    @Modifying
    @Query("update RegionEntity as r set r.key=?1, r.nameUz=?2, r.nameRus=?3, r.nameEng=?4 where r.id=?5")
    void update(String key, String nameUz, String nameRus, String nameEng, Integer regionId);


    Page<RegionEntity> findAllByVisibleTrue(Pageable paging);

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible=false where id=?1")
    void deleteRegion(Integer regionId);


    List<RegionEntity> findAllByVisibleTrue();

    @Query("from RegionEntity where key = ?1")
    RegionEntity getByRegionKey(String regionKey);

}
