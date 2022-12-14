package com.example.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {
    private Integer id;

    @Size(min = 5, message = "Key is wrong")
    private String key;
    @Size(min = 5, message = "Article Type name is wrong")
    private String nameUz;
    @Size(min = 5, message = "Article Type name is wrong")
    private String nameRus;
    @Size(min = 5, message = "Article Type name is wrong")
    private String nameEng;

    private LocalDateTime createdDate;
    private Integer creatorId;
    private String name;
}
