///**
// * @Copyright ®2015 Sinosoft Co. Ltd. All rights reserved.
// * 项目名称 : 三峡人寿业务前置平台系统
// * 创建日期 : 2017-04-15
// * 修改历史 :
// * 1. [2017-04-15]创建文件 by bianzb
// */
//package com.sinosoft.common.database.druid;
//
//import java.sql.SQLException;
//import java.util.Properties;
//
//import javax.annotation.Resource;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.bind.RelaxedPropertyResolver;
//import org.springframework.context.ApplicationContextException;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.util.StringUtils;
//import com.github.pagehelper.PageHelper;
//
///**
// * 【类或接口功能描述】
// * <p>
// * Description:类或接口功能详细描述
// * </p>
// *
// * @author bianzb
// * @version 1.0
// * @date 2017-04-15 22:54
// */
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = { "com.sinosoft.common.dao", "com.sinosoft.common.*.*.mapper", "com.sinosoft.common.*.mapper" })
//public class DatabaseConfiguration implements EnvironmentAware {
//    @Resource
//    private Environment environment;
//    private RelaxedPropertyResolver propertyResolver;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
//    }
//
//    // 注册dataSource
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public DruidDataSource dataSource() throws SQLException {
//        if (StringUtils.isEmpty(propertyResolver.getProperty("url"))) {
//            throw new ApplicationContextException("Database connection pool is not configured correctly");
//        }
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
//        druidDataSource.setUrl(propertyResolver.getProperty("url"));
//        druidDataSource.setUsername(propertyResolver.getProperty("username"));
//        druidDataSource.setPassword(propertyResolver.getProperty("password"));
//        druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
//        druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
//        druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
//        druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
//        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
//        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
//        druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
//        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
//        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
//        druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
//        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
//                Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
//        druidDataSource.setFilters(propertyResolver.getProperty("filters"));
//        return druidDataSource;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        // mybatis分页
//        PageHelper pageHelper = new PageHelper();
//        Properties props = new Properties();
//        props.setProperty("dialect", "mysql");
//        props.setProperty("reasonable", "true");
//        props.setProperty("supportMethodsArguments", "true");
//        props.setProperty("returnPageInfo", "check");
//        props.setProperty("params", "count=countSql");
//        pageHelper.setProperties(props); // 添加插件
//        sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageHelper });
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/*/*Mapper.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource());
//    }
//}
