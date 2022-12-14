package com.example.dto.profile;

import com.example.dto.attach.AttachDTO;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;

    @Size(min = 3, max = 20, message = "Profile name is wrong")
    private String name;

    @Size(min = 3, max = 25, message = "Profile surname is wrong")
    private String surname;

    @Size(min = 4, max = 13, message = "Profile phone is wrong")
    private String phone;

    @Email(message = "Email is wrong")
    private String email;

    @Size(min = 4, message = "Profile password is wrong")
    private String password;

    @Size(min = 4, message = "Profile role is wrong")
    private ProfileRole role;

    @Size(min = 35, message = "Image Id is wrong")
    private String imageId;
    private Boolean visible;
    private ProfileStatus status;
    private Integer creatorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private AttachDTO image;
    private String jwt;
}
