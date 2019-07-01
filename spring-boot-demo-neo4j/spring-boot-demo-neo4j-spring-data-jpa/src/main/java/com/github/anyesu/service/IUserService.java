package com.github.anyesu.service;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.common.base.IBaseService;
import com.github.anyesu.entity.Auth;
import com.github.anyesu.entity.User;
import org.springframework.data.repository.query.Param;

/**
 * 用户
 *
 * @author anyesu
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 查询用户的所有权限 ( @Query 注解方式 )
     *
     * @param id 用户id
     * @return
     */
    Iterable<Auth> findUserAuths(@Param("id") Long id);

    /**
     * 查询用户的所有权限 ( session 方式 )
     *
     * @param id 用户id
     * @return
     */
    Iterable<Auth> findUserAuths2(@Param("id") Long id);

    /**
     * 根据条件分页查找
     *
     * @param query    查询条件
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return
     */
    PageInfo<User> selectPage(User query, Integer pageNum, Integer pageSize);

}
