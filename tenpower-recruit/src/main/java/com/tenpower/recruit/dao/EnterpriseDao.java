package com.tenpower.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenpower.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {
    /**
     * 根据企业热门状态获取企业列表
     *
     * @param ishot
     * @return
     */
    List<Enterprise> findByIshot(String ishot);
}
