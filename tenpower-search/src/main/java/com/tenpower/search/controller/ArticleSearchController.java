package com.tenpower.search.controller;

import com.tenpower.search.pojo.Article;
import com.tenpower.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 文章搜索Controller
 * @Author bofeng
 * @Date 2019/3/31 13:02
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 向索引库添加文章
     * @param article
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Article article) {
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据key搜索文章
     * @param key 搜索关键词
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{key}/{page}/{size}")
    public Result searchByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size) {
        Page<Article> list = articleSearchService.findAllByTitleOrContentLike(key, page, size);
        PageResult pageResult = new PageResult(list.getTotalElements(), list.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
