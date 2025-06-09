package com.ctgu.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ctgu.interceptor.PrintSqlInterceptor;

/**
 */
@Configuration
@MapperScan(basePackages = { "com.ctgu.dao", "com.ctgu.mapper" }, sqlSessionFactoryRef = "coreSqlSessionFactory")
public class CoreSource
{
	@Value("${sql.print:0}")
	private Integer printSql;

	/**
	 * 返回core数据库的数据源
	 * 
	 * @return
	 */
	@Bean(name = "coreDataSource")
	@Primary// 主数据源
	@ConfigurationProperties(prefix = "spring.datasource.druid.core")
	public DataSource dataSource(DruidProperties druidProperties)
	{
		return druidProperties.dataSource(DruidDataSourceBuilder.create().build());
	}

	@Bean(name = "coreMybatisProperties")
	@Primary
	@ConfigurationProperties(prefix = "mybatis-plus.core")
	public MybatisPlusProperties mybatisProperties()
	{
		return new MybatisPlusProperties();
	}

	/**
	 * 返回core数据库的会话工厂
	 * 
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "coreSqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("coreDataSource") DataSource ds,
			@Qualifier("coreMybatisProperties") MybatisPlusProperties mybatisProperties) throws Exception
	{
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(ds);
		bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
		bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage() == null ? "com.ctgu.pojo"
				: mybatisProperties.getTypeAliasesPackage());
		bean.setConfiguration(mybatisProperties.getConfiguration());
		if (printSql == 1)
		{
			bean.getConfiguration().addInterceptor(new PrintSqlInterceptor());
		}
		return bean.getObject();
	}

	/**
	 * 返回core数据库的会话模板
	 * 
	 * @param sessionFactory
	 * @return
	 */
	@Bean(name = "coreSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("coreSqlSessionFactory") SqlSessionFactory sessionFactory)
	{
		return new SqlSessionTemplate(sessionFactory);
	}

	/**
	 * 返回core数据库的事务
	 * 
	 * @param ds
	 * @return
	 */
	@Bean(name = "coreTransactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager(@Qualifier("coreDataSource") DataSource ds)
	{
		return new DataSourceTransactionManager(ds);
	}
}
