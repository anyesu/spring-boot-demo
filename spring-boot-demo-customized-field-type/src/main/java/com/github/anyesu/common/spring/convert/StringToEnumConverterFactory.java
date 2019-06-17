package com.github.anyesu.common.spring.convert;

import com.github.anyesu.common.EnumValue;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * SpringMVC 枚举类型-反序列化
 *
 * @author anyesu
 */
public final class StringToEnumConverterFactory implements ConverterFactory<String, EnumValue> {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EnumValue> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum(targetType);
    }

    private class StringToEnum<T extends Enum<T> & EnumValue> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            source = source.trim();// 去除首尾空白字符
            return source.isEmpty() ? null : EnumValue.valueOf(this.enumType, source);
        }
    }

}
