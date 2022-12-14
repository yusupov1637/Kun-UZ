package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO {
    private Integer id;
    @Email(message = "Email is wrong")
    private String email;

    @Size(min = 3, message = "Message should be at least 3 characters")
    private String message;

    private LocalDateTime createdDate;
}
