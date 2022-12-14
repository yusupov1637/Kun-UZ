package com.example.entity;

import com.example.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String phone;

    @Column
    private String message;

    @Column
    private SmsStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
