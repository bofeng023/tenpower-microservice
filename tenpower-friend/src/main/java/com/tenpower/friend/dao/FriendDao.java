package com.tenpower.friend.dao;

import com.tenpower.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    /**
     * 根据联合主键查找交友
     * @param userid
     * @param friendid
     * @return
     */
    Friend findByUseridAndAndFriendid(String userid, String friendid);

    /**
     * 更改为互相喜欢
     * @param isLike
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "update tb_friend set islike=? where userid=? and friendid=?",nativeQuery = true)
    void updateLike(String isLike, String userId, String friendId);

    /**
     * 删除好友
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "delete from tb_friend where userid=? and friendid=?", nativeQuery = true)
    void remove(String userId, String friendId);
}
