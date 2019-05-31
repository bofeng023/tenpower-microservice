package com.tenpower.spit.dao;

import com.tenpower.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 吐槽数据访问层
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    /**
     * 根据父id分页查吐槽数据
     * @param parentid
     * @return
     */
    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
