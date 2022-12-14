package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;

    @Size(min = 3, message = "Key is required")
    private String key;

    @Size(min = 3, message = "Region Name is required")
    private String nameUz;

    @Size(min = 3, message = "Region Name is required")
    private String nameRus;

    @Size(min = 3, message = "Region Name is required")
    private String nameEng;

    private String name;

    private Boolean visible;
    private Integer creatorId;
    private LocalDateTime createdDate;
}
