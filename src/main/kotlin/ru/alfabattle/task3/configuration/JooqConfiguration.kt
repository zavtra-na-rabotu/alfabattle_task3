package ru.alfabattle.task3.configuration

import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
class JooqConfiguration {
    @Bean
    fun dsl(dataSource: DataSource): DefaultDSLContext {
        System.setProperty("org.jooq.no-logo", "true")

        return DefaultDSLContext(
            DefaultConfiguration()
                .set(DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource)))
        )
    }
}
