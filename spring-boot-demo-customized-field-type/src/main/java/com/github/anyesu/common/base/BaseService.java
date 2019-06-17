package com.github.anyesu.common.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * service基础类，实现常用的单表增删改查
 * <br>示例:
 * <pre> {@code
 * @Service
 * public class DemoServiceImpl extends BaseService<Demo> implements DemoService {
 * }}</pre>
 *
 * @author anyesu
 */
public abstract class BaseService<T> implements IBaseService<T> {

    @Autowired
    protected IBaseMapper<T> mapper;

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T findById(Object id) {
        return id == null ? null : mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int count(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int insertList(List<T> recordList) {
        if (recordList == null || recordList.isEmpty()) {
            return 0;
        }
        return mapper.insertList(recordList);
    }

    @Override
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int insertSelectiveList(List<T> recordList) {
        return mapper.insertSelectiveList(recordList);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteById(Object id) {
        return id == null ? 0 : mapper.deleteByPrimaryKey(id);
    }

}