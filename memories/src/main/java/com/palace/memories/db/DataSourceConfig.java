package com.palace.memories.db;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource(value = { "classpath:db.properties" })
public class DataSourceConfig {
	private static Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix="write")
    public DataSource primaryDataSource() {
		return new DruidDataSource();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix="read")
    public DataSource readDataSource() {
        return new DruidDataSource();
    }
}
