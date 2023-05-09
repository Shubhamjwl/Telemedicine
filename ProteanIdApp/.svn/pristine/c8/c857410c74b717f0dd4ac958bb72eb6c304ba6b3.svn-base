package com.nsdl.authenticate.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "idRepoEntityManagerFactory", transactionManagerRef = "idRepoTransactionManager", basePackages = {
		"com.nsdl.authenticate.id.repository" })
public class IdRepositoryConfig {

	@Autowired
	private Environment env;
	
    @Primary
	@Bean(name = "idRepoDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource idRepoDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.jdbc-url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

    @Primary
	@Bean(name = "idRepoEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("idRepoDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.nsdl.authenticate.id.entity")
				.persistenceUnit("mosip_idrepo").build();
	}

    @Primary
	@Bean(name = "idRepoTransactionManager")
	public PlatformTransactionManager idRepoTransactionManager(
			@Qualifier("idRepoEntityManagerFactory") EntityManagerFactory idRepoEntityManagerFactory) {
		return new JpaTransactionManager(idRepoEntityManagerFactory);
	}
}
