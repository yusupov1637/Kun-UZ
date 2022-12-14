package com.example.controller;

import com.example.dto.article.ArticleLikeDTO;
import com.example.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/create")
    public ResponseEntity<?> createReaction(@RequestBody ArticleLikeDTO dto){
        articleLikeService.putReaction(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateReaction(@RequestBody ArticleLikeDTO dto){
        articleLikeService.update(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeReaction(@RequestBody ArticleLikeDTO dto){
        articleLikeService.remove(dto);
        return ResponseEntity.ok().build();
    }
}
