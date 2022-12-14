package com.example.repository;

import com.example.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmailRepository extends JpaRepository<EmailHistory, Integer> {

    List<EmailHistory> findAllByEmail(String email);

    @Query(value = "SELECT * from email_history where (SELECT cast(created_date as date))=?1", nativeQuery = true)
    List<EmailHistory> getEmailByDate(LocalDate localDate);
}
