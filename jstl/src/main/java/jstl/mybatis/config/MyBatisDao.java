package jstl.mybatis.config;

import java.lang.annotation.*;

/**
 * Created by crabime on 10/31/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MyBatisDao {

    String value() default "";
}
