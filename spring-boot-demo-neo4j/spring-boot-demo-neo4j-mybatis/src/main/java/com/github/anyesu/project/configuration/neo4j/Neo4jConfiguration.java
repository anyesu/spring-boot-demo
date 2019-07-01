package com.github.anyesu.project.configuration.neo4j;

import static com.github.anyesu.project.configuration.BaseConfig.SERVICE_IMPL_PACKAGE_REGEXP;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Neo4j 事务配置
 *
 * @author anyesu
 */
@Configuration
@ConditionalOnClass({PlatformTransactionManager.class})
public class Neo4jConfiguration {

    /**
     * 配置事务管理器
     *
     * @param dataSource 数据源
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 配置声明式事务规则
     *
     * @param txManager 事务管理器
     * @return
     */
    private TransactionInterceptor txInterceptor(PlatformTransactionManager txManager) {
        Properties props = new Properties();
        // 查询方法不需要事务
        props.setProperty("select*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("find*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("count*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("query*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("list*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        // 其他方法默认设置事务
        props.setProperty("*", "PROPAGATION_REQUIRED");

        return new TransactionInterceptor(txManager, props);
    }

    /**
     * 配置声明式事务切面规则
     *
     * @param txManager 事务管理器
     * @return
     */
    @Bean
    public Advisor txAdvisor(PlatformTransactionManager txManager) {
        return new RegexpMethodPointcutAdvisor(SERVICE_IMPL_PACKAGE_REGEXP, txInterceptor(txManager));
    }

}
