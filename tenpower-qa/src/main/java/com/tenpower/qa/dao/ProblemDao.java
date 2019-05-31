package com.tenpower.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenpower.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * 根据标签id分页查询问题按更新时间排序
     *
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem p, tb_pl pl where p.id = pl.problemid and pl.labelid = ? order by p.updatetime desc", nativeQuery = true)
    Page<Problem> findNewProblemsByLabelId(String labelId, Pageable pageable);

    /**
     * 根据标签id分页查询问题按回复数由高到低排序
     *
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem p, tb_pl pl where p.id = pl.problemid and pl.labelid = ? order by p.reply desc ", nativeQuery = true)
    Page<Problem> findHotProblemsByLabelId(String labelId, Pageable pageable);

    /**
     * 根据标签id分页查询未回复的问题按创建时间由高到低排序
     *
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem p, tb_pl pl where p.id = pl.problemid and pl.labelid = ? and p.reply = 0 order by p.createtime desc", nativeQuery = true)
    Page<Problem> findWaitProblemsByLabelId(String labelId, Pageable pageable);
}
