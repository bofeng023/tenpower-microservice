package com.tenpower.search.service;

import com.tenpower.search.dao.ArticleSearchDao;
import com.tenpower.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Description 文章搜索Service
 * @Author bofeng
 * @Date 2019/3/31 12:58
 * @Version 1.0
 */
@Service
public class ArticleSearchService {
    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 添加文章
     * @param article
     */
    public void add(Article article) {
        articleSearchDao.save(article);
    }

    /**
     * 根据key搜索文章
     * @param key 搜索的词
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findAllByTitleOrContentLike(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleSearchDao.findAllByTitleOrContentLike(key, key, pageable);
    }
}
