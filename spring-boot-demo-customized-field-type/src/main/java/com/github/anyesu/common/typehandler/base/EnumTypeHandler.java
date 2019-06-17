package com.github.anyesu.common.typehandler.base;

import com.github.anyesu.common.EnumValue;
import com.github.anyesu.util.GenericsUtils;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Mybatis 枚举类型-类型转换器
 *
 * @author anyesu
 */
public class EnumTypeHandler<T extends Enum<T> & EnumValue> extends BaseTypeHandler<T> {

    private final Class<T> type;

    /**
     * 只能由子类调用
     */
    @SuppressWarnings("unchecked")
    protected EnumTypeHandler() {
        type = GenericsUtils.getSuperClassGenericClass(getClass());
    }

    /**
     * 由 Mybatis 根据类型动态生成实例
     *
     * @param type
     * @see org.apache.ibatis.type.TypeHandlerRegistry#getInstance(Class, Class)
     */
    public EnumTypeHandler(Class<T> rawClass) {
        this.type = rawClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        Object value = parameter.toValue();
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getString(columnIndex));
    }

    private T valueOf(String s) {
        return s == null ? null : EnumValue.valueOf(type, s);
    }
}