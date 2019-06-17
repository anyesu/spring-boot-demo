package com.github.anyesu.common.base;

import com.github.anyesu.common.web.R;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * restful 的基本规范
 * <p>
 * 基本的增删改查
 *
 * @param <T>  实体类型
 * @param <PK> 主键类型
 * @author anyesu
 */
public class RestServiceController<T extends BaseEntity<PK>, PK extends Serializable> {

    /**
     * 泛型注入
     */
    @Autowired(required = false)
    protected IBaseService<T> baseService;

    /**
     * 根据 id 查询资源
     *
     * @param id 资源的唯一标识
     * @return
     */
    @GetMapping("{id}")
    public R findOne(@PathVariable("id") PK id) {
        T t = baseService.findById(id);
        return t == null ? R.error("不存在") : R.success(t);
    }

    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    @GetMapping
    protected R<List<T>> findAll(T entity) {
        return R.success(baseService.select(entity));
    }

    /**
     * 新增一个资源
     *
     * @param entity 资源实体
     * @return
     */
    @PostMapping
    protected R add(@RequestBody T entity) {
        entity.setId(null);
        return R.result(baseService.insertSelective(entity) > 0);
    }

    /**
     * 全量更新一个资源
     *
     * @param id     资源的唯一标识
     * @param entity 资源实体
     * @return
     */
    @PutMapping("{id}")
    protected R updateAll(@PathVariable("id") PK id, @RequestBody T entity) {
        entity.setId(id);
        return R.result(baseService.updateByPrimaryKey(entity) > 0);
    }

    /**
     * 部分更新一个资源，不处理 null 值
     *
     * @param id     资源的唯一标识
     * @param entity 资源实体
     * @return
     */
    @PatchMapping("{id}")
    protected R update(@PathVariable("id") PK id, @RequestBody T entity) {
        entity.setId(id);
        return R.result(baseService.updateByPrimaryKeySelective(entity) > 0);
    }

    /**
     * 删除一个资源
     *
     * @param id 资源的唯一标识
     * @return
     */
    @DeleteMapping("{id}")
    protected R delete(@PathVariable("id") PK id) {
        return R.result(baseService.deleteById(id) > 0);
    }
}
