package cn.crabime.security;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:applicationContextTest.xml"))
public class ResourceMappingTest extends TestCase {

    private JdbcRequestMapBuilder.ResourceMapping resourceMapping;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testResourceMapping(){
        String sql = "select rc.res_string, r.name from role r, resc rc, resc_role rr where r.id = rr.role_id and rc.id = rr.resc_id";
        resourceMapping = new JdbcRequestMapBuilder.ResourceMapping(dataSource, sql);
        List<JdbcRequestMapBuilder.ResourceMapping> list = resourceMapping.execute();
        assertEquals(4, list.size());
    }

}
