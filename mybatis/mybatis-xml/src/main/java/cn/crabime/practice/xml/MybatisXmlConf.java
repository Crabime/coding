package cn.crabime.practice.xml;

import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.mybatis.handler.AutoEnumTypeHandler;
import cn.crabime.practice.xml.interceptor.InsertInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "cn.crabime.practice.xml.dao", sqlSessionTemplateRef = "sqlSessionTemplate")
@ComponentScan(basePackages = "cn.crabime.practice.xml")
public class MybatisXmlConf {

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource());

        // 提前设置TypeHandler
        TypeHandler[] typeHandler = new TypeHandler[1];
        typeHandler[0] = new AutoEnumTypeHandler();
        sqlSessionFactoryBean.setTypeHandlers(typeHandler);

        // 注册别名
        sqlSessionFactoryBean.setTypeAliases(new Class[]{Education.class});

        // 注册拦截器
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new InsertInterceptor()});

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(patternResolver.getResources("classpath:mappings/*.xml"));

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.103.3.149:3306/testdb?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("songsx");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }
}
