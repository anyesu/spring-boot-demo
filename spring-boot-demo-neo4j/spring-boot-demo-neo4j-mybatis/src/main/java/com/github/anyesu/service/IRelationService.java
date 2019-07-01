package com.github.anyesu.service;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;
import java.util.List;

/**
 * 实例关联相关
 *
 * @author anyesu
 */
public interface IRelationService extends IPageService {

    /**
     * 统计关联数
     *
     * @param nodeId 源实例id
     * @return
     */
    int countByNodeId(Long nodeId);

    /**
     * 查询实例的所有已关联对象
     *
     * @param srcId    源实例id
     * @param src      源实例类型
     * @param dest     目标实例类型
     * @param relation 关联类型
     * @return
     */
    List<Neo4jModel> selectNodeRelations(Long srcId, String src, String dest, String relation);

    /**
     * 分页查询实例的关联对象
     *
     * @param srcId        源实例id
     * @param src          源实例类型
     * @param dest         目标实例类型
     * @param relation     关联类型
     * @param haveRelation 是否有关联，null 表示全部
     * @param pageNum      页码
     * @param pageSize     每页大小
     * @return
     */
    PageInfo<Neo4jModel> selectPageNodeRelations(Long srcId, String src, String dest,
                                                 String relation, Boolean haveRelation,
                                                 Integer pageNum, Integer pageSize);

    /**
     * 新增实例关联
     *
     * @param srcId    源实例id
     * @param destId   目标实例id
     * @param src      源实例类型
     * @param dest     目标实例类型
     * @param relation 关联类型
     * @return
     */
    int insert(Long srcId, Long destId, String src, String dest, String relation);

    /**
     * 删除实例关联
     *
     * @param srcId    源实例id
     * @param destId   目标实例id
     * @param src      源实例类型
     * @param dest     目标实例类型
     * @param relation 关联类型
     * @return
     */
    int delete(Long srcId, Long destId, String src, String dest, String relation);

}
