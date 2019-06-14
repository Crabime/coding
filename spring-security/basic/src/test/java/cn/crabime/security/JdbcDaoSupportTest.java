package cn.crabime.security;

import cn.crabime.beans.Role;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:applicationContextTest.xml"))
public class JdbcDaoSupportTest extends TestCase {
    @Autowired
    private DataSource dataSource;

    private MyJdbcDaoSupport jdbcDaoSupport;

    @Before
    public void setUp() throws Exception {
        this.jdbcDaoSupport = new MyJdbcDaoSupport(dataSource);
    }

    @Test
    public void testSelectAllJdbcDaoSupport(){
        JdbcTemplate jdbcTemplate = this.jdbcDaoSupport.getInnerJdbcTemplate();
        String querySql = "select * from role";
        List<Role> roleList = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<Role>(Role.class));
        assertEquals(3, roleList.size());
    }

    /**
     * 使用JdbcTemplate进行select查询
     */
    @Test
    public void testJdbcTemplateCriteriaSearch(){
        JdbcTemplate jdbcTemplate = this.jdbcDaoSupport.getInnerJdbcTemplate();
        String querySql = "select * from role where name = ?";
        List<Role> roles = jdbcTemplate.query(querySql, new Object[]{"ROLE_ADMIN"}, new BeanPropertyRowMapper<Role>(Role.class));
        assertEquals(1, roles.size());
    }

    /**
     * 测试插入对象
     */
    @Test
    public void testInsertObject(){
        JdbcTemplate jdbcTemplate = this.jdbcDaoSupport.getInnerJdbcTemplate();
        String sql = "insert into role(name, des) value(?, ?)";
        final Object[] params = new Object[]{"ROLE_NULL", "位置角色"};
        jdbcTemplate.execute(new SimplePreparedStatementCreator(sql, params), new PreparedStatementCallback<Integer>() {
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setObject(1, params[0]);
                ps.setObject(2, params[1]);
                return ps.executeUpdate();
            }
        });
    }

    /**
     * 测试使用JdbcTemplate根据name删除对象
     */
    @Test
    public void testDeleteObjectByName(){
        JdbcTemplate jdbcTemplate = this.jdbcDaoSupport.getInnerJdbcTemplate();
        String sql = "delete from role where name = ?";
        int result = jdbcTemplate.update(sql, "ROLE_NULL");
        assertEquals(1, result);
    }

    /**
     * 注入数据源
     */
    private class MyJdbcDaoSupport extends JdbcDaoSupport{
        private DataSource dataSource;

        protected MyJdbcDaoSupport(DataSource dataSource){
            setDataSource(dataSource);
        }

        protected JdbcTemplate getInnerJdbcTemplate(){
            return this.getJdbcTemplate();
        }
    }

    /**
     * 执行insert时使用
     * {@link org.springframework.jdbc.core.JdbcTemplate#execute(PreparedStatementCreator, PreparedStatementCallback)}
     * 方法中PreparedStatementCreator的一个具体实现
     */
    private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider{
        private final String sql;
        private final Object[] args;

        public SimplePreparedStatementCreator(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement(this.sql);
        }

        public String getSql() {
            return this.sql;
        }
    }
}
