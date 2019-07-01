package com.github.anyesu.service;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.entity.Neo4jModel;

/**
 * 分页查询
 *
 * @author anyesu
 */
public interface IPageService {

    /**
     * 分页查询
     *
     * @param condition 筛选条件
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @return
     */
    PageInfo<Neo4jModel> selectPage(String condition, Integer pageNum, Integer pageSize);

    /**
     * 分页查询
     *
     * @param condition   筛选条件
     * @param returnValue 返回值
     * @param pageNum     页码
     * @param pageSize    每页大小
     * @return
     */
    PageInfo<Neo4jModel> selectPage(String condition, String returnValue, Integer pageNum, Integer pageSize);

}
