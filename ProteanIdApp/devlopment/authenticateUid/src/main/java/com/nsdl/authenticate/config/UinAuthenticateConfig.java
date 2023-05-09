package com.nsdl.authenticate.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "uinEntityManagerFactory", transactionManagerRef = "uinTransactionManager", basePackages = {
		"com.nsdl.authenticate.uin.repository" })
public class UinAuthenticateConfig {

	@Autowired
	private Environment env;
	
	@Bean(name = "uinDataSource")
	@ConfigurationProperties(prefix = "db2.datasource")
	public DataSource uinRepoDataSource() {
		  DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("db2.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("db2.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("db2.datasource.username"));
        dataSource.setPassword(env.getProperty("db2.datasource.password"));

        return dataSource;
	}

	@Bean(name = "uinEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("uinDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.nsdl.authenticate.uin.entity").persistenceUnit("public")
				.build();
	}
	@Bean(name = "uinTransactionManager")
	public PlatformTransactionManager uinRepoTransactionManager(
			@Qualifier("uinEntityManagerFactory") EntityManagerFactory uinRepoEntityManagerFactory) {
		return new JpaTransactionManager(uinRepoEntityManagerFactory);
	}
}
