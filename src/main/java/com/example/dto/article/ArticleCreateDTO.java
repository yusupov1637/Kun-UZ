package com.example.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDTO {
    private String id;

    @Size(min = 5, message = "Title is wrong")
    private String title;

    @Size(min = 20, message = "Article Description is wrong")
    private String description;

    @Size(min = 50, message = "Article Content is wrong")
    private String content;

    @Size(min = 35, message = "Image Id is wrong")
    private String imageId;

    @Positive(message = "Region Id is wrong")
    private Integer regionId;

    @Positive(message = "Category Id is wrong")
    private Integer categoryId;
}
