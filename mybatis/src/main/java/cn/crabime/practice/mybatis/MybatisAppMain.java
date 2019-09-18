package cn.crabime.practice.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "cn.crabime.practice.mybatis.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@ComponentScan(basePackages = "cn.crabime.practice.mybatis")
public class MybatisAppMain {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.register(Grade.class, AutoEnumTypeHandler.class);
        return sqlSessionFactory;
    }

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.103.3.149:8080/genedb");
        dataSource.setUsername("root");
        dataSource.setPassword("yuanqin");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(MybatisAppMain.class);
        FamilyService familyService = atx.getBean(FamilyService.class);
        Family family = new Family("Crabime", Grade.FATHER);
        familyService.insertFamily(family);
        System.out.println("插入主键为：" + family.getId());
    }
}
