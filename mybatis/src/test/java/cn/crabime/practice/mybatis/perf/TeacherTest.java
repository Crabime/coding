package cn.crabime.practice.mybatis.perf;

import junit.framework.TestCase;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeacherTest extends TestCase {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    protected void setUp() throws Exception {
        final Reader reader = Resources.getResourceAsReader("cn/crabime/practice/mybatis/perf/mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        reader.close();
    }

    @Test
    public void testInsertTeacher() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            Teacher teacher = new Teacher();
            teacher.setName("随机值开始");
            mapper.insertTeacher(teacher);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertOneMillionRecord() {
        String[] names = {"杨坤", "朱旭", "王东海", "Alia"};
        Random random = new Random();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            final int size = 10000;
            final int times = 1000;
            for (int x = 0; x < times; x++) {
                List<Teacher> teacherList = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Teacher teacher = new Teacher();

                    teacher.setName(names[random.nextInt(4)]);

                    teacher.setAge(random.nextInt(50));
                    teacher.setNature(RandomUtil.generate(30));
                    teacherList.add(teacher);
                }
                mapper.batchInsertTeacher(teacherList);
            }
        } finally {
            sqlSession.close();
        }
    }
}
