package com.github.anyesu.common.typehandler.base;

import com.alibaba.fastjson.JSON;
import com.github.anyesu.util.GenericsUtils;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * JSON 类型-类型转换器
 * <p>
 * 由于 Mybatis 对泛型嵌套做了处理，T 为泛型类型时会被转为 rawType，所以要注意以下两点：
 * <p>
 * 1. 不要使用父类的 rawType 属性，使用本类的 type 属性来做类型转换
 * 2. 需要做类型转换的字段要指定 TypeHandler，而不能由 Mybatis 自动查找
 *
 * @author anyesu
 * @see org.apache.ibatis.type.TypeReference#getSuperclassTypeParameter
 */
@Slf4j
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private final Type type;

    /**
     * 只能由子类调用
     */
    protected JsonTypeHandler() {
        type = GenericsUtils.getSuperClassGenericType(getClass());
    }

    /**
     * 由 Mybatis 根据类型动态生成实例
     *
     * @param type
     * @see org.apache.ibatis.type.TypeHandlerRegistry#getInstance(Class, Class)
     */
    public JsonTypeHandler(Class<T> rawClass) {
        this.type = rawClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    private String toJson(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * 反序列化
     *
     * @param content
     * @return
     */
    private T toObject(String content) {
        T object = null;

        if (content != null && content.length() > 0) {
            try {
                object = JSON.parseObject(content, getType());
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return object;
    }

    public Type getType() {
        return type;
    }

}
