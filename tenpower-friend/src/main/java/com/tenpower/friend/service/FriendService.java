package com.tenpower.friend.service;

import com.tenpower.friend.client.UserClient;
import com.tenpower.friend.dao.FriendDao;
import com.tenpower.friend.dao.NoFriendDao;
import com.tenpower.friend.pojo.Friend;
import com.tenpower.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 交友业务类
 * @Author bofeng
 * @Date 2019/4/15 12:30
 * @Version 1.0
 */
@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    /**
     * 添加好友，如果互相喜欢则设置islike为1
     * @param userId
     * @param friendId
     * @return 0:添加成功 1:重复添加 2:添加自己 3:好友id不存在
     */
    public int addFriend(String userId, String friendId) {
        Friend friend = friendDao.findByUseridAndAndFriendid(userId, friendId);
        //判断重复添加
        if (friend != null) {
            return 1;
        }
        //判断添加自己
        if (userId.equals(friendId)) {
            return 2;
        }
        //判断好友id不存在
        if (userClient.findById(friendId).getData() == null) {
            return 3;
        }
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        //设置相互喜欢
        if (friendDao.findByUseridAndAndFriendid(friendId, userId) != null) {
            friendDao.updateLike("1",userId, friendId);
            friendDao.updateLike("1", friendId,userId);
        }
        //更新关注数和粉丝数
        userClient.incFollowsAndFans(friendId, "1");
        return 0;
    }

    /**
     * 添加非好友
     * @param userId
     * @param noFriendId
     */
    public void addNoFriend(String userId, String noFriendId) {
        if (noFriendDao.findByUseridAndNofriendid(userId, noFriendId) == null) {
            NoFriend noFriend = new NoFriend();
            noFriend.setUserid(userId);
            noFriend.setNofriendid(noFriendId);
            noFriendDao.save(noFriend);
        }
    }

    /**
     * 删除好友
     * @param userId
     * @param friendId
     * @return  true:删除成功 false:无效删除
     */
    public boolean deleteFriend(String userId, String friendId) {
        Friend friend = friendDao.findByUseridAndAndFriendid(userId, friendId);
        //判断数据库是否有该好友记录
        if (friend == null) {
            return false;
        }
        friendDao.remove(userId, friendId);
        //取消互相喜欢
        if (friendDao.findByUseridAndAndFriendid(friendId, userId) != null) {
            friendDao.updateLike("0", friendId, userId);
        }
        //更新关注数和粉丝数
        userClient.incFollowsAndFans(friendId, "0");
        return true;
    }

    /**
     * 移除黑名单
     * @param userId
     * @param noFriendId
     * @return
     */
    public boolean deleteNoFriend(String userId, String noFriendId) {
        NoFriend noFriend = noFriendDao.findByUseridAndNofriendid(userId, noFriendId);
        //判断数据库是否有该拉黑记录
        if (noFriend == null) {
            return false;
        }
        noFriendDao.remove(userId, noFriendId);
        return true;
    }
}
