///**
// * @Copyright ®2015 Sinosoft Co. Ltd. All rights reserved.
// * 项目名称 : 三峡人寿业务前置平台系统
// * 创建日期 : 2017-04-15
// * 修改历史 :
// * 1. [2017-04-15]创建文件 by bianzb
// */
//package com.sinosoft.common.database.druid;
//
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 【类或接口功能描述】
// * <p>Description:类或接口功能详细描述 </p>
// *
// * @author bianzb
// * @version 1.0
// * @date 2017-04-15 23:00
// */
//@Configuration
//public class DruidConfig {
//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        ServletRegistrationBean reg = new ServletRegistrationBean();
//        reg.setServlet(new StatViewServlet());
//        reg.addUrlMappings("/druid/*");
//        //reg.addInitParameter("allow", "127.0.0.1"); //白名单
//        //reg.addInitParameter("deny",""); //黑名单
//        reg.addInitParameter("loginUsername", "admin");
//        reg.addInitParameter("loginPassword", "admin");
//        return reg;
//    }
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
//}
