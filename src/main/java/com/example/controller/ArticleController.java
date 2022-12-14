package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleShortInfo;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody ArticleDTO articleDTO) {
        Integer adminId = JwtUtil.getIdFromHeader(request, ProfileRole.MODERATOR);

        ArticleDTO response = articleService.create(adminId, articleDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateById(HttpServletRequest request,
                                        @PathVariable String id,
                                         @RequestBody ArticleDTO articleDTO) {
        JwtUtil.getIdFromHeader(request, ProfileRole.MODERATOR);

        ArticleDTO response = articleService.updateById(id, articleDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
                                        @PathVariable String id) {
        JwtUtil.getIdFromHeader(request, ProfileRole.MODERATOR);

        Boolean response = articleService.deleteById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/status")
    public ResponseEntity<?> changeStatus(HttpServletRequest request,
                                          @RequestParam String id,
                                          @RequestParam ArticleStatus status) {
        Integer publisherId = JwtUtil.getIdFromHeader(request, ProfileRole.PUBLISHER);

        String response = articleService.changeStatus(id, status, publisherId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/last_five")
    public ResponseEntity<?> lastFive(@RequestParam ArticleStatus status) {
        List<ArticleDTO> response = articleService.lastFive(status);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/last_three")
    public ResponseEntity<?> lastThree(@RequestParam ArticleStatus status) {
        List<ArticleDTO> response = articleService.lastThree(status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/last_eight")
    public ResponseEntity<?> lastEight( @RequestBody List<String> idList) {
        List<ArticleShortInfo> response = articleService.lastEight1(idList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "UZBEK") Language language) {
        ArticleDTO articleDTO = articleService.getById(id, language);
        return ResponseEntity.ok(articleDTO);
    }

    @GetMapping("/last_four/{id}")
    public ResponseEntity<?> getLastFour(@PathVariable String id) {
        List<ArticleShortInfo> response = articleService.getLastFour(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/most_reads")
    public ResponseEntity<?> getMostReads(){
        List<ArticleShortInfo> response = articleService.getMostReads();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/last_five/region/{regionKey}")
    public ResponseEntity<?> getLastFiveByRegionKey(@PathVariable String regionKey){
        List<ArticleShortInfo> response = articleService.getLastFiveByRegionKey(regionKey);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagination/region/{regionKey}")
    public ResponseEntity<?> getPaginationByRegionKey(@PathVariable String regionKey,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer size){
        Page<ArticleShortInfo> response = articleService.getPaginationByRegionKey(regionKey, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/last_five/region/{categoryKey}")
    public ResponseEntity<?> getLastFiveByCategoryKey(@PathVariable String categoryKey){
        List<ArticleShortInfo> response = articleService.getLastFiveByCategoryKey(categoryKey);
        return ResponseEntity.ok(response);
    }
}
