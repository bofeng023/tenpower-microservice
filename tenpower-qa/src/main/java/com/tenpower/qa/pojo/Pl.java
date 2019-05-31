package com.tenpower.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description 问题回答中间表实体
 * @Author bofeng
 * @Date 2019/3/12 22:22
 * @Version 1.0
 */
@Entity
@Table(name = "tb_pl")
public class Pl implements Serializable {
    @Id
    private String problemid;
    @Id
    private String labelid;

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }
}
