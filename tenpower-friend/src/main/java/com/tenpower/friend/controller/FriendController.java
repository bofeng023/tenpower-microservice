package com.tenpower.friend.controller;

import com.tenpower.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author bofeng
 * @Date 2019/4/15 18:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 添加好友或拉黑
     *
     * @param friendId
     * @param type     0:拉黑 1:加好友
     * @return
     */
    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable String friendId, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        //判断权限
        if (claims != null && "user".equals(claims.get("roles"))) {
            //如果是喜欢
            if ("1".equals(type)) {
                //添加好友,该方法在添加成功时才会执行
                int result = friendService.addFriend(claims.getId(), friendId);
                if (result == 0) {
                    //移除黑名单
                    friendService.deleteNoFriend(claims.getId(), friendId);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
                if (result == 1) {
                    return new Result(false, StatusCode.REPERROR, "不能重复添加");
                }
                if (result == 2) {
                    return new Result(false, StatusCode.ERROR, "不能添加自己为好友");
                }
                return new Result(false, StatusCode.ERROR, "好友id不存在");
            } else if ("0".equals(type)) {
                //如果不喜欢
                //删除好友
                boolean flag = friendService.deleteFriend(claims.getId(), friendId);
                if (flag) {
                    //添加非好友
                    friendService.addNoFriend(claims.getId(), friendId);
                    return new Result(true, StatusCode.OK, "删除成功");
                }
                return new Result(false, StatusCode.REPERROR, "无效删除");
            }
            return new Result(false, StatusCode.ERROR, "无效参数");
        }
        return new Result(false, StatusCode.ACCESSERROR, "权限不足");
    }

    /**
     * 删除好友
     *
     * @param friendId
     * @return
     */
    @DeleteMapping("/{friendId}")
    public Result deleteFriend(@PathVariable String friendId) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims != null && "user".equals(claims.get("roles"))) {
            boolean flag = friendService.deleteFriend(claims.getId(), friendId);
            if (flag) {
                return new Result(true, StatusCode.OK, "删除成功");
            }
            return new Result(false, StatusCode.REPERROR, "无效删除");
        }
        return new Result(false, StatusCode.ACCESSERROR, "权限不足");
    }

    /**
     * 移除黑名单
     *
     * @param noFriendId
     * @return
     */
    @DeleteMapping("/{noFriendId}")
    public Result deleteNoFriend(@PathVariable String noFriendId) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims != null && "user".equals(claims.get("roles"))) {
            boolean flag = friendService.deleteNoFriend(claims.getId(), noFriendId);
            if (flag) {
                return new Result(true, StatusCode.OK, "删除成功");
            }
            return new Result(false, StatusCode.REPERROR, "无效删除");
        }
        return new Result(false, StatusCode.ACCESSERROR, "权限不足");
    }
}
