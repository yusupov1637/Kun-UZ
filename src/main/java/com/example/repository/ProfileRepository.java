package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    ProfileEntity findByPhone(String phone);

    ProfileEntity findByPhoneAndPassword(String phone, String password);

    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity as p set p.name=?1, p.surname=?2, p.visible=?3, p.role=?4, p.status=?5 where p.id=?6")
    int updateProfileByAdmin(String name, String surname, Boolean visible, ProfileRole role, ProfileStatus status, Integer profileId);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name=?1, surname=?2, password=?3 where id=?4")
    int updateUserById(String name, String surname, String password, Integer profileId);

    Optional<ProfileEntity> findAllByRole(ProfileRole admin);

    @Transactional
    @Modifying
    @Query("update ProfileEntity as p set p.visible = false where p.id=?1")
    void deleteProfileVisibility(Integer profileId);
}
