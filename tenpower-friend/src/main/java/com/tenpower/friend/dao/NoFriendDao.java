package com.tenpower.friend.dao;

import com.tenpower.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    /**
     * 根据用户id和黑名单id查找
     * @param userid
     * @param nofriendid
     * @return
     */
    NoFriend findByUseridAndNofriendid(String userid, String nofriendid);

    /**
     * 移除黑名单
     * @param userId
     * @param nofriendId
     */
    @Modifying
    @Query(value = "delete from tb_nofriend where userid=? and nofriendid=?", nativeQuery = true)
    void remove(String userId, String nofriendId);
}
