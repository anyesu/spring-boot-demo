package com.github.anyesu.common.base;

import com.github.anyesu.common.PageInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author anyesu
 */
public abstract class BaseService<T> implements IBaseService<T> {

    /**
     * 泛型注入
     */
    @Autowired
    protected BaseRepository<T> repository;

    @Autowired
    protected Session session;

    @Override
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public <S extends T> S save(S s, int depth) {
        return repository.save(s, depth);
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return repository.save(entities);
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities, int depth) {
        return repository.save(entities, depth);
    }

    @Override
    public T findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public T findOne(Long id, int depth) {
        return repository.findOne(id, depth);
    }

    @Override
    public boolean exists(Long id) {
        return repository.exists(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<T> findAll(int depth) {
        return repository.findAll(depth);
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Iterable<T> findAll(Sort sort, int depth) {
        return repository.findAll(sort, depth);
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> ids) {
        return repository.findAll(ids);
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> ids, int depth) {
        return repository.findAll(ids, depth);
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> ids, Sort sort) {
        return repository.findAll(ids, sort);
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> ids, Sort sort, int depth) {
        return repository.findAll(ids, sort, depth);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable, int depth) {
        return repository.findAll(pageable, depth);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        repository.delete(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * 分页查询
     *
     * @param condition 筛选条件
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @return
     */
    protected PageInfo<T> selectPage(String condition, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        List<T> list;

        // 1. 查询总数据量
        String cypher = condition + " RETURN COUNT(*) AS count";
        Map<String, ?> parameters = Collections.emptyMap();
        Result result = session.query(cypher, parameters);
        int count = convertResultToCount(result);

        if (count > 0) {
            // 2. 分页查询
            int startRow = (pageNum - 1) * pageSize;
            cypher = condition + String.format(" RETURN n SKIP %d LIMIT %d", startRow, pageSize);
            result = session.query(cypher, parameters);
            list = convertResultToList(result);
        } else {
            // 当查询总数为 0 时，直接返回空的结果
            list = Collections.emptyList();
        }

        // 3. 设置分页查询结果
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    private int convertResultToCount(Result result) {
        List<Integer> counts = new ArrayList<>();
        result.forEach(i -> counts.add(Integer.valueOf(i.get("count").toString())));
        return counts.isEmpty() ? 0 : counts.get(0);
    }

    private List<T> convertResultToList(Result result) {
        List<T> list = new ArrayList<>();
        result.forEach(i -> list.add((T) i.get("n")));
        return list;
    }

}
