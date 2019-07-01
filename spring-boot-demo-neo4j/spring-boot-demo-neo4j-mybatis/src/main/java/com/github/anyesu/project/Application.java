package com.github.anyesu.project;

import static com.github.anyesu.project.configuration.BaseConfig.CONFIGURATION_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.CONTROLLER_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.MAPPER_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.SERVICE_IMPL_PACKAGE;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动入口
 * <p>
 * scanBasePackages   扫描 配置类/Controller/Service
 * <p>
 * {@link MapperScan} 扫描 Mybatis Mapper
 *
 * @author anyesu
 */
@SpringBootApplication(
        scanBasePackages = {
                CONFIGURATION_PACKAGE, CONTROLLER_PACKAGE, SERVICE_IMPL_PACKAGE
        }
)
@MapperScan(MAPPER_PACKAGE)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
