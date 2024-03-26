package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.service.ArticleService;
import org.springframework.stereotype.Controller;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

}
