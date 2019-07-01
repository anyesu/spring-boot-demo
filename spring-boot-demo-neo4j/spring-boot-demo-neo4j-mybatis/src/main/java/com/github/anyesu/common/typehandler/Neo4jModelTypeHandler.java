package com.github.anyesu.common.typehandler;

import com.alibaba.fastjson.JSON;
import com.github.anyesu.entity.Neo4jModel;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Neo4j 结果集转换
 *
 * @author anyesu
 */
@Slf4j
public class Neo4jModelTypeHandler extends BaseTypeHandler<Neo4jModel> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Neo4jModel parameter, JdbcType jdbcType)
            throws SQLException {
        // 不支持 BigDecimal , 需要特殊处理
        parameter.forEach((k, v) -> convertBigDecimal(parameter, k, v));
        ps.setObject(i, parameter);
    }

    private void convertBigDecimal(Neo4jModel neo4jModel, String key, Object value) {
        if (value instanceof BigDecimal) {
            neo4jModel.put(key, value.toString());
        }
    }

    @Override
    public Neo4jModel getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertToModel(rs.getObject(columnName));
    }

    @Override
    public Neo4jModel getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertToModel(rs.getObject(columnIndex));
    }

    @Override
    public Neo4jModel getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertToModel(cs.getObject(columnIndex));
    }

    /**
     * 转换返回结果
     *
     * @param object 只允许为 {@link Map} 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    private Neo4jModel convertToModel(Object object) {
        if (object instanceof Map) {
            try {
                Neo4jModel neo4jModel = new Neo4jModel((Map<String, Object>) object);
                // TODO 下面的转换不一定需要
                convertModelField(neo4jModel, "_id", "id");
                convertModelField(neo4jModel, "_labels", "labels");
                convertModelField(neo4jModel, "_bind", "@bind");
                return neo4jModel;
            } catch (ClassCastException ignore) {
            }
        }

        if (object != null) {
            log.warn("Neo4j 返回未知类型[{}]结果: {}", object.getClass().getName(), JSON.toJSONString(object));
        }

        return object == null ? null : Neo4jModel.EMPTY;
    }

    /**
     * 转换字段名
     *
     * @param neo4jModel
     * @param oldKey
     * @param newKey
     */
    private void convertModelField(Neo4jModel neo4jModel, String oldKey, String newKey) {
        Object value = neo4jModel.remove(oldKey);
        if (value != null) {
            neo4jModel.put(newKey, value);
        }
    }

}
