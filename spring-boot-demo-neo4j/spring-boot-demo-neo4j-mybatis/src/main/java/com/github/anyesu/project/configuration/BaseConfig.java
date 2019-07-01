package com.github.anyesu.project.configuration;

/**
 * 项目基础配置
 *
 * @author anyesu
 */
@SuppressWarnings("WeakerAccess")
public class BaseConfig {

    /**
     * 项目基础包名称 ( 修改为 project 包的上级 )
     */
    public static final String BASE_PACKAGE = "com.github.anyesu";

    /**
     * 配置类所在包
     */
    public static final String CONFIGURATION_PACKAGE = BASE_PACKAGE + ".project.configuration";

    /**
     * 实体类所在包
     */
    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".entity";

    /**
     * Mapper 所在包
     */
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";

    /**
     * Service 所在包
     */
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

    /**
     * ServiceImpl 所在包
     */
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";

    /**
     * ServiceImpl 所在包的正则表达式
     */
    public static final String SERVICE_IMPL_PACKAGE_REGEXP = SERVICE_IMPL_PACKAGE + ".*";

    /**
     * Controller 所在包
     */
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";

}
