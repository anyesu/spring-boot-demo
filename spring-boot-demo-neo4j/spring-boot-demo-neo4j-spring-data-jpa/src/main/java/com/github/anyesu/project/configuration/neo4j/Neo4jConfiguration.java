package com.github.anyesu.project.configuration.neo4j;

import static com.github.anyesu.project.configuration.BaseConfig.SERVICE_IMPL_PACKAGE_REGEXP;

import java.util.Properties;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Neo4j 事务配置
 *
 * @author anyesu
 */
@Configuration
public class Neo4jConfiguration {

    /**
     * 配置Neo4j事务管理器
     *
     * @param sessionFactory
     * @param transactionManagerCustomizers
     * @return
     */
    @Bean
    public Neo4jTransactionManager transactionManager(SessionFactory sessionFactory,
                                                      ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        return customize(new Neo4jTransactionManager(sessionFactory), transactionManagerCustomizers.getIfAvailable());
    }

    private Neo4jTransactionManager customize(Neo4jTransactionManager transactionManager,
                                              TransactionManagerCustomizers customizers) {
        if (customizers != null) {
            customizers.customize(transactionManager);
        }
        return transactionManager;
    }

    /**
     * 配置声明式事务规则
     *
     * @return
     */
    private TransactionInterceptor txInterceptor(Neo4jTransactionManager txManager) {
        Properties props = new Properties();
        // 查询方法不需要事务
        props.setProperty("select*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("find*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
        props.setProperty("exists*", "PROPAGATION_NOT_SUPPORTED,-Throwable,readOnly");
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
     * @return
     */
    @Bean
    public Advisor txAdvisor(Neo4jTransactionManager txManager) {
        // 注意: repository 层已经有事务了，不要再去拦截
        return new RegexpMethodPointcutAdvisor(SERVICE_IMPL_PACKAGE_REGEXP, txInterceptor(txManager));
    }
}
