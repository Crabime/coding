package com.gooalgene.mybatis.autoconstructor;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by crabime on 11/13/17.
 */
public class AutoConstructorTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setUp() throws IOException, SQLException {
        final Reader reader = Resources.getResourceAsReader("com/gooalgene/mybatis/autoconstructor/mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        reader.close(); //reader任务完成
    }

    @Test
    public void fullyPopulatedSubject(){
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            final AutoConstrcutMapper mapper = sqlSession.getMapper(AutoConstrcutMapper.class);
            final PrimitiveSubject subject = mapper.getSubject(1);
            Assert.assertNotNull(subject);
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 如果查询结果中有null值,并且该属性还为基础数据类型,那么这里会出现反射异常
     */
    @Test(expected = PersistenceException.class)
    public void primitiveSubject(){
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            final AutoConstrcutMapper mapper = sqlSession.getMapper(AutoConstrcutMapper.class);
            mapper.getSubjects();
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void getWrapperSubjects(){
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            final AutoConstrcutMapper mapper = sqlSession.getMapper(AutoConstrcutMapper.class);
            List<WrapperSubject> wrapperSubjects = mapper.getWrapperSubjects();
            Assert.assertNotNull(wrapperSubjects);
            Assert.assertEquals(3, wrapperSubjects.size());
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 如果有两个相同的构造方法,但是一个是primitive type的,那么对于null数据返回错误,
     */
    @Test
    public void getAnnotatedSubjects(){
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            final AutoConstrcutMapper mapper = sqlSession.getMapper(AutoConstrcutMapper.class);
            List<AnnotatedSubject> annotatedSubjects = mapper.getAnnotatedSubjects();
            Assert.assertNotNull(annotatedSubjects);
            Assert.assertEquals(3, annotatedSubjects.size());
        }finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void getBadSubjects(){
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            final AutoConstrcutMapper mapper = sqlSession.getMapper(AutoConstrcutMapper.class);
            mapper.getBadSubjects();
        }finally {
            sqlSession.close();
        }
    }

}
