package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            HttpServletRequest request) {
        Integer adminId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        CategoryDTO response = categoryService.create(categoryDTO, adminId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer categoryId,
                                          @Valid @RequestBody CategoryDTO categoryDTO,
                                          HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        CategoryDTO response = categoryService.update(categoryId, categoryDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer categoryId,
                                        HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        String response = categoryService.deleteById(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategoryList(HttpServletRequest request,
                                           @RequestParam Integer page,
                                           @RequestParam Integer size) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        Page<CategoryDTO> response = categoryService.getList(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/language")
    public ResponseEntity<?> getByLanguage(@RequestParam Language lang) {
        List<CategoryDTO> response = categoryService.getByLanguage(lang);
        return ResponseEntity.ok(response);
    }

}
