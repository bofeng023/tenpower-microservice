package com.tenpower.spit.controller;

import com.tenpower.spit.pojo.Spit;
import com.tenpower.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 吐槽控制层
 * @Author bofeng
 * @Date 2019/3/17 13:53
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查所有
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id查
     *
     * @param spitId
     * @return
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "查询成功", spit);
    }

    /**
     * 添加
     *
     * @param spit
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改
     *
     * @param spitId
     * @param spit
     * @return
     */
    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spitService.update(spitId, spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据id删除
     *
     * @param spitId
     * @return
     */
    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级id查
     *
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentId(parentId, page, size);
        PageResult pageResult = new PageResult(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 点赞
     *
     * @param spitId
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbUp(@PathVariable String spitId) {
        //TODO 取用户id
        String userId = "2019";
        //判断该用户是否已对该吐槽点赞。其中String类比较不能用“==”！
        if (redisTemplate.opsForValue().get("thumbUp_" + userId + "_" + spitId).equals("thumbed")) {
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
        spitService.thumbUp(spitId);
        redisTemplate.opsForValue().set("thumbUp_" + userId + "_" + spitId, "thumbed");
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
