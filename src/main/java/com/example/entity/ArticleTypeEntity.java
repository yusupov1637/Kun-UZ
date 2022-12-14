package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String key = String.valueOf(UUID.randomUUID());

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRus;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "creator_id")
    private Integer creatorId;

    @Column
    private Boolean visible = Boolean.TRUE;
}
