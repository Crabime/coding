package cn.crabime.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 实现InitializingBean主要是为了在初始化bean之前先获取到资源权限Map集合
 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    /*
     * 资源权限集合
     * RequestMatcher用来匹配HttpServletRequest
     */
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    /*
     * 查找数据库和资源权限关系
     */
    private JdbcRequestMapBuilder builder;

    public void afterPropertiesSet() throws Exception {
        this.requestMap = this.bindRequestMap();
    }

    /**
     * 访问某个资源文件需要的权限
     * 该方法在每次发起http请求时均会进入
     */
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        Set<ConfigAttribute> roles = new HashSet<ConfigAttribute>(); //将某一个URL匹配的ROLE全部存入Set中，保证不重复
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            //如果请求路径与存入的RequestMatcher匹配，则拿到该URL相应的用户权限，匹配的具体实现采用的是Spring中AntPathRequestMatcher
            if (entry.getKey().matches(httpRequest)){
                Collection<ConfigAttribute> all = entry.getValue();
                if (all != null && all.size() > 0) {
                    roles.addAll(all);
                }
            }

        }
        return roles;
    }

    /**
     * 获取所有权限
     * 该方法只会在系统启动时执行
     */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        System.out.println("总共有这些权限："+allAttributes.toString());
        return allAttributes;
    }

    //判断传入的class类型是否与FilterInvocation相同或者是它的父类、父接口
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    //绑定requestMap
    protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap() {
        return builder.buildRequestMap();
    }

    public JdbcRequestMapBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(JdbcRequestMapBuilder builder) {
        this.builder = builder;
    }
}
