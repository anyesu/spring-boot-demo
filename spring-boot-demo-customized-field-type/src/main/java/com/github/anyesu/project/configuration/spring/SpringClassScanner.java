package com.github.anyesu.project.configuration.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

/**
 * Spring 类扫描器
 *
 * @author anyesu
 */
@Slf4j
@Component
public class SpringClassScanner implements ResourceLoaderAware {

    private ResourcePatternResolver resolver;

    private MetadataReaderFactory metaReader;

    /**
     * 通过 Spring 注入 {@link ResourceLoader}
     *
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metaReader = new CachingMetadataReaderFactory(resourceLoader);
    }

    /**
     * 扫描指定包路径下所有的类 ( 包括内部类 )
     *
     * @param basePackage 扫描路径
     * @return 类集合
     */
    public List<Class<?>> scanClass(String basePackage) {
        return scanClass(basePackage, null);
    }

    /**
     * 扫描指定包路径下所有的类 ( 包括内部类 )
     *
     * @param basePackage 扫描路径
     * @param predicate   过滤条件
     * @return 类集合
     */
    public List<Class<?>> scanClass(String basePackage, Predicate<Class<?>> predicate) {
        List<Class<?>> list = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        for (String className : scanClassName(basePackage)) {
            try {
                Class<?> clazz = classLoader.loadClass(className);
                if (predicate == null || predicate.test(clazz)) {
                    list.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                log.error("", e);
            }
        }
        return list;
    }

    /**
     * 扫描指定包路径下所有的类 ( 包括内部类 )
     *
     * @param basePackage 扫描路径
     * @return 类名集合
     */
    public List<String> scanClassName(String basePackage) {
        List<String> list = new ArrayList<>();
        try {
            String path = packageToPath(basePackage);
            String locationPattern = String.format("classpath*:%s/**/*.class", path);
            Resource[] resources = resolver.getResources(locationPattern);

            for (Resource r : resources) {
                MetadataReader reader = metaReader.getMetadataReader(r);
                list.add(reader.getClassMetadata().getClassName());
            }
        } catch (IOException e) {
            log.error("", e);
        }
        return list;
    }

    /**
     * 包名转换为路径名
     *
     * @param pkg 包名
     * @return 路径名
     */
    private static String packageToPath(String pkg) {
        return pkg.replaceAll("\\.", "/");
    }
}
