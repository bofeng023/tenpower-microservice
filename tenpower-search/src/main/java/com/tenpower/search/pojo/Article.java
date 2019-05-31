package com.tenpower.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @Description 文章索引库实体类
 * @Author bofeng
 * @Date 2019/3/30 16:44
 * @Version 1.0
 */
@Document(indexName = "tenpower", type = "article")
public class Article implements Serializable {
    @Id
    private String id;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title; //文章标题

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;//文章正文

    private String status; //审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
