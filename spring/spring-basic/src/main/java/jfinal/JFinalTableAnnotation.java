package jfinal;

import java.lang.annotation.*;

/**
 * Created by crabime on 6/27/17.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JFinalTableAnnotation {
    String dataSourceName() default "";
    String tableName() default "";
    String primaryColumn() default "";
}
