package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;

    @Size(min = 3, message = "Key is wrong")
    private String key;

    @Size(min = 3, message = "Category nameUz is wrong")
    private String nameUz;

    @Size(min = 3, message = "Category nameRu is wrong")
    private String nameRu;

    @Size(min = 3, message = "Category nameEn is wrong")
    private String nameEng;

    private Boolean visible;
    private LocalDateTime createdDate;
    private Integer creatorId;
    private String name;
}
