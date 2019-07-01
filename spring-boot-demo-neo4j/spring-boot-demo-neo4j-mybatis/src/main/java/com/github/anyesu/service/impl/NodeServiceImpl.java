package com.github.anyesu.service.impl;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;
import com.github.anyesu.mapper.INodeMapper;
import com.github.anyesu.service.INodeService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 实例相关
 *
 * @author anyesu
 */
@Service
public class NodeServiceImpl extends PageServiceImpl implements INodeService {

    @Resource
    private INodeMapper nodeMapper;

    @Override
    public Neo4jModel findById(Long id) {
        return nodeMapper.findById(id);
    }

    @Override
    public Neo4jModel findByTypeAndId(String type, Long id) {
        return nodeMapper.findByTypeAndId(type, id);
    }

    @Override
    public List<Neo4jModel> selectByType(String type) {
        return nodeMapper.selectByType(type);
    }

    @Override
    public PageInfo<Neo4jModel> selectPageByType(String type, Neo4jModel query, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(type)) {
            return new PageInfo<>(Collections.emptyList());
        }
        String condition = String.format("MATCH (n:%s)", type);
        return selectPage(condition, pageNum, pageSize);
    }

    @Override
    public int insert(String type, Neo4jModel data) {
        if (StringUtils.isBlank(type) || data == null) {
            return 0;
        }
        filterData(data);
        return nodeMapper.insert(type, data);
    }

    @Override
    public int updateById(Long id, Neo4jModel data) {
        if (id == null || data == null) {
            return 0;
        }
        filterData(data);
        return nodeMapper.updateById(id, new Neo4jModel(data));
    }

    @Override
    public int deleteById(Long id) {
        return nodeMapper.deleteById(id);
    }

    /**
     * 数据过滤
     *
     * @param object 数据
     */
    private void filterData(Neo4jModel object) {
        // 内置属性不允许被修改
        for (String key : Neo4jModel.INTERNAL_KEYS) {
            object.remove(key);
        }
    }

}
