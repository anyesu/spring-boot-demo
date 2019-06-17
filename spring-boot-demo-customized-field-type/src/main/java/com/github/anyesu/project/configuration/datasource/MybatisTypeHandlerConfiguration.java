package com.github.anyesu.project.configuration.datasource;

import static com.github.anyesu.project.configuration.BaseConfig.ENTITY_PACKAGE;

import com.github.anyesu.common.EnumValue;
import com.github.anyesu.common.typehandler.base.EnumTypeHandler;
import com.github.anyesu.common.typehandler.base.JsonTypeHandler;
import com.github.anyesu.entity.Address;
import com.github.anyesu.entity.UserDetail;
import com.github.anyesu.project.configuration.spring.SpringClassScanner;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 类型转换器配置
 *
 * @author anyesu
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class})
public class MybatisTypeHandlerConfiguration {

    private TypeHandlerRegistry typeHandlerRegistry;

    private final SpringClassScanner springClassScanner;

    public MybatisTypeHandlerConfiguration(SqlSessionFactory sqlSessionFactory, SpringClassScanner springClassScanner) {
        this.typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        this.springClassScanner = springClassScanner;
    }

    /**
     * 注册 Mybatis 类型转换器
     */
    @Autowired
    public void registerTypeHandlers() {
        jsonTypes().forEach(this::registerJsonTypeHandler);
        enumValues().forEach(this::registerEnumTypeHandler);
    }

    /**
     * 注册 JSON 类型的类型转换器
     *
     * @param javaTypeClass Java 类型
     */
    private void registerJsonTypeHandler(Class<?> javaTypeClass) {
        register(javaTypeClass, JsonTypeHandler.class);
    }

    /**
     * 注册 枚举 类型的类型转换器
     *
     * @param javaTypeClass Java 类型
     */
    private void registerEnumTypeHandler(Class<?> javaTypeClass) {
        register(javaTypeClass, EnumTypeHandler.class);
    }

    /**
     * 注册类型转换器
     *
     * @param javaTypeClass    Java 类型
     * @param typeHandlerClass 类型转换器类型
     */
    private void register(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        this.typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
    }

    /**
     * 简单 JSON 类型
     *
     * @return
     */
    private List<Class<?>> jsonTypes() {
        // TODO 这里为了方便就硬编码记录类型，自行替换扫描的方式
        return Arrays.asList(UserDetail.class, Address.class);
    }

    /**
     * 扫描所有的 {@link EnumValue} 实现类
     * 注册到 Spring 中
     *
     * @return 类集合
     */
    @Bean
    public List<Class<?>> enumValues() {
        // 过滤自定义枚举类
        Predicate<Class<?>> filter = clazz -> clazz.isEnum() && EnumValue.class.isAssignableFrom(clazz);
        return springClassScanner.scanClass(ENTITY_PACKAGE, filter);
    }

}
