package com.example.controller;

import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article_view")
public class ArticleViewCount {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/encrease_to_one/{articleId}")
    public ResponseEntity<?> increaseViewCount(@PathVariable String articleId){
         articleService.increaseViewCount(articleId);
        return  new ResponseEntity<String>("View count increased to 1",HttpStatus.ACCEPTED);
    }

    @GetMapping("/share_to_one/{articleId}")
    public ResponseEntity<?> shareCount(@PathVariable String articleId){
         articleService.increaseViewCount(articleId);
        return  new ResponseEntity<String>("Share count increased to 1",HttpStatus.ACCEPTED);
    }
}
