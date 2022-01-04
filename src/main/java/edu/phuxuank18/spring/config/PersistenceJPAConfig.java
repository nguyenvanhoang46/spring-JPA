package edu.phuxuank18.spring.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:database.properties" })
@ComponentScan({ "edu.phuxuank18.spring" })
@EnableJpaRepositories(basePackages = { "edu.phuxuank18.spring.repository" })
public class PersistenceJPAConfig {

	@Autowired
	private Environment env;

	public PersistenceJPAConfig() {
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "edu.phuxuank18.spring.model" });

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(additionalProperties());

		return entityManagerFactoryBean;
	}

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty(DIALECT, env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty(SHOW_SQL, env.getProperty("hibernate.showSql"));
		hibernateProperties.setProperty(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.put("hibernate.connection.CharSet", env.getProperty("hibernate.connection.CharSet"));
		hibernateProperties.put("hibernate.connection.characterEncoding",
				env.getProperty("hibernate.connection.characterEncoding"));
		hibernateProperties.put("hibernate.connection.useUnicode", env.getProperty("hibernate.connection.useUnicode"));
		return hibernateProperties;
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("hibernate.connection.driverClass"));
		dataSource.setUrl(env.getProperty("hibernate.connection.url"));
		dataSource.setUsername(env.getProperty("hibernate.connection.username"));
		dataSource.setPassword(env.getProperty("hibernate.connection.password"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}