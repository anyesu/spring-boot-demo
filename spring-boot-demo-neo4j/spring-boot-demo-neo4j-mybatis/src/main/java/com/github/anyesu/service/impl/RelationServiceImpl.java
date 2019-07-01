package com.github.anyesu.service.impl;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;
import com.github.anyesu.mapper.IRelationMapper;
import com.github.anyesu.service.IRelationService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 实例关联相关
 *
 * @author anyesu
 */
@Service
public class RelationServiceImpl extends PageServiceImpl implements IRelationService {

    @Resource
    private IRelationMapper relationMapper;

    @Override
    public int countByNodeId(Long nodeId) {
        return relationMapper.countByNodeId(nodeId);
    }

    @Override
    public List<Neo4jModel> selectNodeRelations(Long srcId, String src, String dest, String relation) {
        return relationMapper.selectNodeRelations(srcId, src, dest, relation);
    }

    @Override
    public PageInfo<Neo4jModel> selectPageNodeRelations(Long srcId, String src, String dest,
                                                        String relation, Boolean haveRelation,
                                                        Integer pageNum, Integer pageSize) {
        if (srcId == null
                || StringUtils.isBlank(src)
                || StringUtils.isBlank(relation)
                || StringUtils.isBlank(dest)) {
            return new PageInfo<>(Collections.emptyList());
        }

        StringBuilder condition = new StringBuilder();
        condition.append(String.format("MATCH (a:%s) -[:%s]-> (b:%s) ", src, relation, dest))
                 .append(String.format("WHERE id(a) = %d ", srcId))
                 .append("WITH COLLECT(DISTINCT ID(b)) AS binds ");

        condition.append(String.format("MATCH (n:%s) ", dest));

        if (Boolean.TRUE.equals(haveRelation)) {
            condition.append("WHERE ID(n) IN binds ");
        } else if (Boolean.FALSE.equals(haveRelation)) {
            condition.append("WHERE NOT ID(n) IN binds ");
        }

        String returnValue = "n {.*, _id: ID(n), _bind: ID(n) IN binds}";
        return selectPage(condition.toString(), returnValue, pageNum, pageSize);
    }

    @Override
    public int insert(Long srcId, Long destId, String src, String dest, String relation) {
        return relationMapper.insert(srcId, destId, src, dest, relation);
    }

    @Override
    public int delete(Long srcId, Long destId, String src, String dest, String relation) {
        return relationMapper.delete(srcId, destId, src, dest, relation);
    }
}
