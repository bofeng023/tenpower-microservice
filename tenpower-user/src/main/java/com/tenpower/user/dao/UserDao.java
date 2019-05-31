package com.tenpower.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenpower.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据手机号查用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 变更用户关注数
     * @param x 变更数
     * @param userId
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET followcount=followcount+? WHERE id=?", nativeQuery = true)
    void updateFollows(int x, String userId);

    /**
     * 变更好友粉丝数
     * @param x 变更数
     * @param friendId
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET fanscount=fanscount+? WHERE id=?", nativeQuery = true)
    void updateFans(int x, String friendId);
}
