package com.github.anyesu.project;

import static com.github.anyesu.project.configuration.BaseConfig.CONFIGURATION_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.CONTROLLER_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.ENTITY_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.REPOSITORY_PACKAGE;
import static com.github.anyesu.project.configuration.BaseConfig.SERVICE_IMPL_PACKAGE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * 项目启动入口
 * <p>
 * scanBasePackages                扫描 配置类/Controller/Service
 * <p>
 * {@link EntityScan}              扫描实体类
 * <p>
 * {@link EnableNeo4jRepositories} 扫描 Neo4j Repository
 *
 * @author anyesu
 */
@SpringBootApplication(
        scanBasePackages = {
                CONFIGURATION_PACKAGE, CONTROLLER_PACKAGE, SERVICE_IMPL_PACKAGE
        }
)
@EntityScan(ENTITY_PACKAGE)
@EnableNeo4jRepositories(REPOSITORY_PACKAGE)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
