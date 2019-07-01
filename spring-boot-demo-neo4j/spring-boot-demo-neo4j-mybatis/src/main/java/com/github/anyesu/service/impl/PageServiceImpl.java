package com.github.anyesu.service.impl;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;
import com.github.anyesu.mapper.IPageMapper;
import com.github.anyesu.service.IPageService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;

/**
 * 分页查询
 *
 * @author anyesu
 */
public class PageServiceImpl implements IPageService {

    @Resource
    private IPageMapper pageMapper;

    @Override
    public PageInfo<Neo4jModel> selectPage(String condition, Integer pageNum, Integer pageSize) {
        return selectPage(condition, "n", pageNum, pageSize);
    }

    @Override
    public PageInfo<Neo4jModel> selectPage(String condition, String returnValue, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        List<Neo4jModel> list;

        // 1. 查询总数据量
        int count = pageMapper.countByCondition(condition);

        if (count > 0) {
            // 2. 分页查询
            int startRow = (pageNum - 1) * pageSize;
            list = pageMapper.findPageByCondition(condition, returnValue, startRow, pageSize);
        } else {
            // 当查询总数为 0 时，直接返回空的结果
            list = Collections.emptyList();
        }

        // 3. 设置分页查询结果
        PageInfo<Neo4jModel> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(count);
        return pageInfo;
    }

}
