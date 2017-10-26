package jfinaltest;

import jfinal.entity.Member;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.beans.PropertyDescriptor;

/**
 * Created by crabime on 7/2/17.
 */
public class BeanUtilTest {
    @Test
    public void testBeanUtils(){
        Member member = new Member();
        member.setId(1000L);
        member.setName("张三");
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(member);
        try {
            if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                for (PropertyDescriptor descriptor : propertyDescriptors) {
                    String propertyName = descriptor.getName();
                    if (!propertyName.equals("class")) {
                        Class<?> type = PropertyUtils.getPropertyType(member, propertyName);
                        String simpleName = type.getSimpleName(); //这里将类型全部转换成包装类
                        if (StringUtils.equals(simpleName, "Integer")){
                            Integer intValue = null;
                            
                        }
                    }
                }
            }
        }catch (Exception e){
            System.err.println("类型转换出错了");
        }
    }
}
