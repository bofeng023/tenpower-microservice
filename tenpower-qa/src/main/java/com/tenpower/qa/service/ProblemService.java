package com.tenpower.qa.service;

import com.tenpower.qa.dao.ProblemDao;
import com.tenpower.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap) {

        return new Specification<Problem>() {

            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
                }
                // 内容
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
                }
                // 是否解决
                if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                    predicateList.add(cb.like(root.get("solve").as(String.class), "%" + searchMap.get("solve") + "%"));
                }
                // 回复人昵称
                if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                    predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + searchMap.get("replyname") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 根据标签id分页查最新问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findNewProblemsByLabelId(String labelId, int page, int size) {
        //jpa的page默认从0开始
        Pageable pageable = PageRequest.of(page - 1, size);
        //若labelId为0则查所有labelId
        if (labelId.equals("0")) {
            return problemDao.findAll(pageable);
        }
        Page<Problem> problems = problemDao.findNewProblemsByLabelId(labelId, pageable);
        return problems;
    }

    /**
     * 根据标签id分页查最热问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findHotProblemsByLabelId(String labelId, int page, int size) {
        //jpa的page默认从0开始
        Pageable pageable = PageRequest.of(page - 1, size);
        //若labelId为0则查所有labelId
        if (labelId.equals("0")) {
            return problemDao.findAll(pageable);
        }
        Page<Problem> problems = problemDao.findHotProblemsByLabelId(labelId, pageable);
        return problems;
    }

    /**
     * 根据标签id分页查等待回答问题列表
     *
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findWaitProblemsByLabelId(String labelId, int page, int size) {
        //jpa的page默认从0开始
        Pageable pageable = PageRequest.of(page - 1, size);
        //若labelId为0则查所有labelId
        if (labelId.equals("0")) {
            return problemDao.findAll(pageable);
        }
        Page<Problem> problems = problemDao.findWaitProblemsByLabelId(labelId, pageable);
        return problems;
    }
}
