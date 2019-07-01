package com.github.anyesu.mapper;

import com.github.anyesu.entity.Neo4jModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 实例关联相关
 *
 * @author anyesu
 */
public interface IRelationMapper {

    /**
     * 统计关联数
     *
     * @param nodeId 源实例id
     * @return
     */
    int countByNodeId(@Param("nodeId") Long nodeId);

    /**
     * 查询实例的所有已关联对象
     *
     * @param srcId    源实例id
     * @param src      源实例类型
     * @param dest     目标实例类型
     * @param relation 关联类型
     * @return
     */
    List<Neo4jModel> selectNodeRelations(@Param("id") Long srcId,
                                         @Param("src") String src,
                                         @Param("dest") String dest,
                                         @Param("relation") String relation);

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
    int insert(@Param("srcId") Long srcId,
               @Param("destId") Long destId,
               @Param("src") String src,
               @Param("dest") String dest,
               @Param("relation") String relation);

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
    int delete(@Param("srcId") Long srcId,
               @Param("destId") Long destId,
               @Param("src") String src,
               @Param("dest") String dest,
               @Param("relation") String relation);

}
