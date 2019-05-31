package com.tenpower.qa.controller;

import com.tenpower.qa.client.LabelClient;
import com.tenpower.qa.pojo.Problem;
import com.tenpower.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LabelClient labelClient;
    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        if (request.getAttribute("token_user") == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据标签id分页查最新问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/newlist/{labelId}/{page}/{size}")
    public Result findNewProblemsByLabelId(@PathVariable String labelId,@PathVariable int page,@PathVariable int size) {
        Page<Problem> list = problemService.findNewProblemsByLabelId(labelId, page, size);
        PageResult pageResult = new PageResult(list.getTotalElements(),list.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据标签id分页查最热问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public Result findHotProblemsByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> list = problemService.findHotProblemsByLabelId(labelId, page, size);
        PageResult pageResult = new PageResult(list.getTotalElements(),list.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据标签id分页查最热问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public Result findWaitProblemsByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> list = problemService.findWaitProblemsByLabelId(labelId, page, size);
        PageResult pageResult = new PageResult(list.getTotalElements(),list.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据标签id查找标签
     * @param labelId
     * @return
     */
    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable String labelId) {
        return labelClient.findByLabelId(labelId);
    }
}
