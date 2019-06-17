package com.github.anyesu.project.configuration.web;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.anyesu.common.fastjson.convert.EnumConverter;
import com.github.anyesu.common.spring.convert.StringToEnumConverterFactory;
import com.github.anyesu.enums.Sex;
import com.github.anyesu.project.configuration.datasource.MybatisTypeHandlerConfiguration;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringMVC 配置
 *
 * @author anyesu
 */
@Slf4j
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 配置 {@link HttpMessageConverter}<br>
     * 这种配置方式优先级比较低，使用下面的方式 ( {@link #fastJsonHttpMessageConverter(FastJsonConfig)} )
     * 来配置默认的 {@link HttpMessageConverter}
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // converters.add(new FastJsonHttpMessageConverter());
    }

    /**
     * 使用 fastjson 作为 JSON 类型的 {@link HttpMessageConverter}
     * 这种方式会把对应的 converter 放到 converters 的头部，即最高优先级
     *
     * @return
     */
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter(FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        return converter;
    }

    /**
     * fastjson 配置
     *
     * @param enumValues 自定义枚举类型 {@link MybatisTypeHandlerConfiguration#enumValues()}
     * @return
     */
    @Bean
    public FastJsonConfig fastjsonConfig(@Qualifier("enumValues") List<Class<?>> enumValues) {
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);

        // TODO 这里只是为了测试, 最好都通过扫描来查找而不是硬编码
        enumValues.add(Sex.class);

        if (enumValues != null && enumValues.size() > 0) {
            // 枚举类型字段：序列化反序列化配置
            EnumConverter enumConverter = new EnumConverter();
            ParserConfig parserConfig = config.getParserConfig();
            SerializeConfig serializeConfig = config.getSerializeConfig();
            for (Class<?> clazz : enumValues) {
                parserConfig.putDeserializer(clazz, enumConverter);
                serializeConfig.put(clazz, enumConverter);
            }
        }

        return config;
    }

    /**
     * 向 Spring 注册枚举-类型格式转换器
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

}
