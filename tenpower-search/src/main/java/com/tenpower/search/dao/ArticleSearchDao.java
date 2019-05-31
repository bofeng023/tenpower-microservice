package com.tenpower.search.dao;

import com.tenpower.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章搜索Dao
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {
    /**
     * 根据标题或内容搜索文章
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findAllByTitleOrContentLike(String title, String content, Pageable pageable);
}
