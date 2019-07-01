package com.github.anyesu.service;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * 实例相关
 *
 * @author anyesu
 */
@Service
public interface INodeService extends IPageService {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return
     */
    Neo4jModel findById(Long id);

    /**
     * 根据主键和类型查找
     *
     * @param type 类型
     * @param id   主键
     * @return
     */
    Neo4jModel findByTypeAndId(String type, Long id);

    /**
     * 根据类型查找
     *
     * @param type 类型
     * @return
     */
    List<Neo4jModel> selectByType(String type);

    /**
     * 根据类型分页查找
     *
     * @param type     类型
     * @param query    查询条件
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return
     */
    PageInfo<Neo4jModel> selectPageByType(String type, Neo4jModel query, Integer pageNum, Integer pageSize);

    /**
     * 根据类型新增
     *
     * @param type 类型
     * @param data 数据
     * @return
     */
    int insert(String type, Neo4jModel data);

    /**
     * 根据主键更新
     *
     * @param id   主键
     * @param data 数据
     * @return
     */
    int updateById(Long id, Neo4jModel data);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return
     */
    int deleteById(Long id);

}
