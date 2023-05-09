package com.nsdl.telemedicine.patient.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {
	
	@Bean(name = "postgresDb")
	@ConfigurationProperties(prefix = "spring.ds_post")
	public DataSource postgresDataSource() {
		return  DataSourceBuilder.create().build();
	}

	@Bean(name = "postgresJdbcTemplate")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDb") 
                                              DataSource dsPostgres) {
		return new JdbcTemplate(dsPostgres);
	}
}
