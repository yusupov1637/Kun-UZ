package com.example.controller;

import com.example.dto.article.ArticleTypeDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article/type")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<?> createArticleType(HttpServletRequest request,
                                               @RequestBody ArticleTypeDTO articleTypeDTO) {
        Integer adminId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        ArticleTypeDTO response = articleTypeService.create(adminId, articleTypeDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{articleId}")
    public ResponseEntity<?> updateArticleType(@PathVariable Integer articleId,
                                               @RequestBody ArticleTypeDTO articleTypeDTO,
                                               HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        ArticleTypeDTO response = articleTypeService.update(articleId, articleTypeDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer articleId,
                                        HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        String response = articleTypeService.deleteById(articleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getArticleTypeList(HttpServletRequest request,
                                                @RequestParam Integer page,
                                                @RequestParam Integer size){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Page<ArticleTypeDTO> response = articleTypeService.getListPagination(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/language")
    public ResponseEntity<?> getByLanguage(@RequestParam Language lang){
        List<ArticleTypeDTO> response = articleTypeService.getByLanguage(lang);
        return ResponseEntity.ok(response);
    }
}
