package com.tenpower.user.controller;

import com.tenpower.user.pojo.User;
import com.tenpower.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @PostMapping("/register/{code}")
    public Result add(@RequestBody User user, @PathVariable String code) {
        userService.add(user, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        if (request.getAttribute("claims_admin") == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        String token = (String) request.getAttribute("claims_admin");
        if (token == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User customerUser = userService.findByMobileAndPassword(user.getMobile(), user.getPassword());
        if (customerUser != null) {
            String token = jwtUtil.createJWT(customerUser.getId(), customerUser.getMobile(), "user");
            Map map = new HashMap();
            map.put("token", token);
            map.put("role", "user");
            return new Result(true, StatusCode.OK, "登录成功", map);
        }
        return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
    }

    /**
     * 更新用户关注数和好友粉丝数
     *
     * @param friendId
     * @param flag     非0:用户关注数+1，好友粉丝数+1 0:反之
     * @return
     */
    @PutMapping("/{friendId}/{flag}")
    public Result incFollowsAndFans(@PathVariable String friendId, @PathVariable int flag) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims != null && "user".equals(claims.get("roles"))) {
            if (flag == 0) {
                userService.incFollowsAndFans(claims.getId(), friendId, false);
            } else {
                userService.incFollowsAndFans(claims.getId(), friendId, true);
            }
            return new Result(true, StatusCode.OK, "更新成功");
        }
        return new Result(true, StatusCode.ACCESSERROR, "权限不足");
    }
}
