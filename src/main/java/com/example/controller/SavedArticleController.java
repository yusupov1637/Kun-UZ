package com.example.controller;

import com.example.dto.article.SavedArticleDTO;
import com.example.service.SavedArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved_article")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSavedArticle(@Valid @RequestBody SavedArticleDTO dto){
        SavedArticleDTO response = savedArticleService.create(dto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteSavedArticle(@RequestBody SavedArticleDTO dto){
        String response = savedArticleService.delete(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getList(){
        List<SavedArticleDTO> response = savedArticleService.getList();
        return ResponseEntity.ok(response);
    }
}
