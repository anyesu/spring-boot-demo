package com.github.anyesu.common.fastjson.convert;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.github.anyesu.common.EnumValue;
import java.lang.reflect.Type;

/**
 * fastjson 枚举类型-类型转换器
 *
 * @author anyesu
 */
public class EnumConverter implements ObjectSerializer, ObjectDeserializer {

    /**
     * fastjson 序列化
     *
     * @param serializer
     * @param object
     * @param fieldName
     * @param fieldType
     * @param features
     */
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        object = object instanceof EnumValue ? ((EnumValue) object).toValue() : object;
        serializer.write(object);
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }

    /**
     * fastjson 反序列化
     *
     * @param parser
     * @param type
     * @param fieldName
     * @param <T>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Class enumType = (Class) type;

        // 类型校验：枚举类型并且实现 EnumValue 接口
        if (!enumType.isEnum() || !EnumValue.class.isAssignableFrom(enumType)) {
            return null;
        }

        final JSONLexer lexer = parser.lexer;
        final int token = lexer.token();
        Object value = null;
        if (token == JSONToken.LITERAL_INT) {
            value = lexer.integerValue();
        } else if (token == JSONToken.LITERAL_STRING) {
            value = lexer.stringVal();
        } else if (token != JSONToken.NULL) {
            value = parser.parse();
        }

        return (T) EnumValue.valueOf(enumType, value);
    }
}
