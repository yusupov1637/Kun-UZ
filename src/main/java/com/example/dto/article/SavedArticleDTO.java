package com.example.dto.article;

import com.example.dto.article.ArticleDTO;
import com.example.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedArticleDTO {
    private Integer id;
    private ProfileDTO profile;
    private ArticleDTO article;
    private LocalDateTime createdDate;

    @NotBlank
    private String articleId;
    @Positive
    private Integer profileId;
}
