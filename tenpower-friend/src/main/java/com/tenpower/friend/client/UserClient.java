package com.tenpower.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 用户客户端
 */
@FeignClient("tenpower-user")
public interface UserClient {
    /**
     * 变更关注数和粉丝数
     *
     * @param friendId
     * @param flag
     * @return
     */
    @PutMapping("/user/{friendId}/{flag}")
    Result incFollowsAndFans(@PathVariable("friendId") String friendId, @PathVariable("flag") String flag);

    /**
     * 根据用户id查找用户
     *
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    Result findById(@PathVariable("id") String id);
}
