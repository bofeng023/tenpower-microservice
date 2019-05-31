package com.tenpower.base.controller;

import com.tenpower.base.pojo.Label;
import com.tenpower.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 标签控制层
 * @Author bofeng
 * @Date 2019/3/11 15:18
 * @Version 1.0
 */
@RestController //相当于@Controller和@ResponseBody
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 查询全部列表
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id查询标签
     *
     * @param labelId
     * @return
     */
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable String labelId) {
        Label label = labelService.findById(labelId);
        return new Result(true, StatusCode.OK, "查询成功", label);
    }

    /**
     * 添加标签
     *
     * @param label
     */
    @PostMapping
    public Result add(@RequestBody Label label) { //将前端传来的json转为对象需要@RequestBody
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 更改标签
     *
     * @param label
     */
    @PutMapping("/{labelId}")
    public Result update(@RequestBody Label label, @PathVariable String labelId) {
        labelService.update(label,labelId);
        return new Result(true, StatusCode.OK, "更改成功");
    }

    /**
     * 删除标签
     *
     * @param labelId
     * @return
     */
    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
