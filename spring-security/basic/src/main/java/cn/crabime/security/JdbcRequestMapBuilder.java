package cn.crabime.security;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class JdbcRequestMapBuilder extends JdbcDaoSupport {

    private String resourceQuery = "";

    public String getResourceQuery() {
        return resourceQuery;
    }

    public void setResourceQuery(String resourceQuery) {
        this.resourceQuery = resourceQuery;
    }

    //找到所有用户ROLE_NAME与URL之间的对应关系
    public List<Resource> findResources() {
        ResourceMapping resourceMapping = new ResourceMapping(getDataSource(), resourceQuery);
        return resourceMapping.execute();
    }

    /**
     * 对每个URL可允许访问的ConfigAttribute，也就是允许访问的用户
     *
     * @return URL与用户的映射
     */
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
        List<Resource> resources = this.findResources();
        //这个地方遍历有问题
        for (Resource resource : resources) {
            //拿到匹配资源路径
            RequestMatcher requestMatcher = this.getRequestMatcher(resource.getUrl());
            //该路径下所有合法的用户
            List<ConfigAttribute> list = null;
            Collection<ConfigAttribute> previousConfigAttributeList = requestMap.get(requestMatcher); //拿到原始某一个URL下的权限集合
            if (previousConfigAttributeList == null || previousConfigAttributeList.size() == 0) {
                list = new ArrayList<ConfigAttribute>();
            } else {
                list = new ArrayList<ConfigAttribute>(previousConfigAttributeList); //一个URL对应多个ROLE_情况
            }
            list.add(new SecurityConfig(resource.getRole()));
            requestMap.put(requestMatcher, list);
        }
        return requestMap;
    }

    //通过一个字符串地址构建一个AntPathRequestMatcher，这里也可以是模糊匹配
    public RequestMatcher getRequestMatcher(String url) {
        return new AntPathRequestMatcher(url);
    }

    private static class Resource {
        private String url;
        private String role;

        public Resource(String url, String role) {
            this.url = url;
            this.role = role;
        }

        public String getUrl() {
            return url;
        }

        public String getRole() {
            return role;
        }
    }

    /**
     * 查找资源映射，这里使用 MappingSqlQuery只要是为了复用JDBC查询
     */
    protected static class ResourceMapping extends MappingSqlQuery {

        protected ResourceMapping(DataSource source, String resourceQuery) {
            super(source, resourceQuery);
            compile();
        }

        /**
         * 获取Resource对象
         */
        protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            String url = rs.getString(1);
            String role = rs.getString(2);
            Resource resource = new Resource(url, role);
            return resource;
        }
    }
}
