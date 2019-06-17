package com.github.anyesu.common.base;

import java.util.List;

/**
 * Mapper 基础接口，实现常用的单表增删改查
 * <br>示例:
 * <br>IDemoMapper.java
 * <pre> {@code
 * public interface IDemoMapper extends IBaseMapper<Demo> {
 * }}</pre>
 *
 * <br>Demo.java
 * <pre> {@code
 * @Data
 * public class Demo {
 *
 *     private Integer id;
 *
 *     private String name;
 *
 * }}</pre>
 *
 * <br>DemoMapper.xml
 * <pre> {@code
 * <?xml version="1.0" encoding="UTF-8" ?>
 * <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
 * <mapper namespace="com.github.anyesu.mapper.IDemoMapper">
 *
 * </mapper>
 * }</pre>
 *
 * @author anyesu
 */
public interface IBaseMapper<T> {

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 根据主键查询模型
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(Object id);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    List<T> select(T entity);

    /**
     * 查询全部结果
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity
     * @return
     */
    int selectCount(T entity);

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     *
     * @param recordList
     * @return
     */
    int insertList(List<T> recordList);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    int insertSelective(T entity);

    /**
     * 批量插入，支持批量插入的数据库可以使用，null的属性不会保存，会使用数据库默认值，例如MySQL,H2等
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     *
     * @param recordList
     * @return
     */
    int insertSelectiveList(List<T> recordList);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据主键字段进行物理删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Object id);

}