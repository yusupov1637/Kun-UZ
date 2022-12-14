package com.example.dto.attach;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AttachDTO {
    private String id;
    private String url;
    private String origenName;
    private String path;
    private Long size;
    private LocalDateTime createdDate;
}
