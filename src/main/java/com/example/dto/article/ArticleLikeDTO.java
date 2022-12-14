package com.example.dto.article;

import com.example.dto.article.ArticleDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.enums.ArticleLikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {
    private Integer id;
    private String articleId;
    private Integer profileId;
    private LocalDateTime createdDate;
    private ArticleLikeStatus status;

    private ArticleDTO article;
    private ProfileDTO profile;
}
