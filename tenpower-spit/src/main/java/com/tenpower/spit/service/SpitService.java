package com.tenpower.spit.service;

import com.tenpower.spit.dao.SpitDao;
import com.tenpower.spit.pojo.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 吐槽业务类
 * @Author bofeng
 * @Date 2019/3/17 13:34
 * @Version 1.0
 */
@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查全部记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据id查文档
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        Optional<Spit> optional = spitDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 添加文档
     *
     * @param spit
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setState("1"); //设为可见
        spit.setComment(0);
        spit.setThumbup(0);
        spitDao.save(spit);
        if (StringUtils.isNotBlank(spit.getParentid())) {
            Optional<Spit> optional = spitDao.findById(spit.getParentid());
            if (optional.isPresent()) {
                Spit parentSpit = optional.get();
                parentSpit.setComment(parentSpit.getComment() + 1);
                spitDao.save(parentSpit);
            }
        }
    }

    /**
     * 更新文档
     *
     * @param spit
     */
    public void update(String spitId, Spit spit) {
        spit.set_id(spitId);
        spitDao.save(spit);
    }

    /**
     * 删除文档
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 根据上级id查吐槽
     *
     * @param parentId 父id
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    /**
     * 点赞
     * @param spitId
     * TODO 不能自己给自己点赞
     */
    public void thumbUp(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
