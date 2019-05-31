package com.tenpower.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description 非好友实体类
 * @Author bofeng
 * @Date 2019/4/15 22:31
 * @Version 1.0
 */
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
public class NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String nofriendid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNofriendid() {
        return nofriendid;
    }

    public void setNofriendid(String nofriendid) {
        this.nofriendid = nofriendid;
    }
}
