package com.github.anyesu.mapper;

import com.github.anyesu.entity.Neo4jModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 实例相关
 *
 * @author anyesu
 */
public interface INodeMapper {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return
     */
    Neo4jModel findById(@Param("id") Long id);

    /**
     * 根据主键和类型查找
     *
     * @param type 类型
     * @param id   主键
     * @return
     */
    Neo4jModel findByTypeAndId(@Param("type") String type, @Param("id") Long id);

    /**
     * 根据类型查找
     *
     * @param type 类型
     * @return
     */
    List<Neo4jModel> selectByType(@Param("type") String type);

    /**
     * 根据类型新增
     *
     * @param type 类型
     * @param data 数据
     * @return
     */
    int insert(@Param("type") String type, @Param("data") Neo4jModel data);

    /**
     * 根据主键更新
     *
     * @param id   主键
     * @param data 数据
     * @return
     */
    int updateById(@Param("id") Long id, @Param("data") Neo4jModel data);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return
     */
    int deleteById(@Param("id") Long id);

}
