package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "image_id")
    private String imageId;
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Integer viewCount = 0;
}
