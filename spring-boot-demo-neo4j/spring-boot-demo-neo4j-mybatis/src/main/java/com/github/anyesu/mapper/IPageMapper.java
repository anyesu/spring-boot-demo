package com.github.anyesu.mapper;

import com.github.anyesu.entity.Neo4jModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 分页查询
 *
 * @author anyesu
 */
public interface IPageMapper {

    /**
     * 根据筛选条件查询数量
     *
     * @param condition 条件
     * @return
     */
    int countByCondition(@Param("condition") String condition);

    /**
     * 分页查询
     *
     * @param condition   筛选条件
     * @param returnValue 返回值
     * @param startRow    起始
     * @param pageSize    每页大小
     * @return
     */
    List<Neo4jModel> findPageByCondition(@Param("condition") String condition,
                                         @Param("return") String returnValue,
                                         @Param("startRow") int startRow,
                                         @Param("pageSize") int pageSize);

}
