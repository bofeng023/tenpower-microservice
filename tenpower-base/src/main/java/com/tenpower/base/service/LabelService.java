package com.tenpower.base.service;

import com.tenpower.base.dao.LabelDao;
import com.tenpower.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.List;
import java.util.Optional;

/**
 * @Description 标签业务逻辑类
 * @Author bofeng
 * @Date 2019/3/11 14:58
 * @Version 1.0
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    public Label findById(String id) {
        Optional<Label> optional = labelDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 添加标签
     * @param label
     */
    public void add(Label label) {
        Long id = idWorker.nextId();
        label.setId(id.toString());
        //如果在数据库中主键不存在，save方法会执行添加操作
        labelDao.save(label);
    }

    /**
     * 更改标签
     * @param label
     */
    public void update(Label label,String labelId) {
        label.setId(labelId);
        //如果主键在数据库中已存在，save方法会执行更新操作
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }
}
