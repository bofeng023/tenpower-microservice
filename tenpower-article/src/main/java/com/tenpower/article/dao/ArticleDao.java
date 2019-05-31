package com.tenpower.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenpower.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    /**
     * 审核
     *
     * @param articleId
     */
    @Modifying
    @Query(value = "update tb_article set state = 1 where id = ?", nativeQuery = true)
    void examine(String articleId);

    /**
     * 点赞
     *
     * @param articleId
     */
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup + 1 where id = 1", nativeQuery = true)
    void thumbUp(String articleId);
}
