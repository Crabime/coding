package bean;

import com.google.common.truth.ExpectFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by crabime on 9/7/17.
 */
@RunWith(JUnit4.class)
public class BeanUtilsTest {
    @Rule
    public final ExpectFailure expectFailure = new ExpectFailure();

    @Test
    public void testInitializeWithoutArg() {
        Angel angle = BeanUtils.instantiate(Angel.class);
        assertThat(angle.toString()).isEqualTo("Angel");
    }

    @Test(expected = BeanInstantiationException.class)
    public void testInitializeInterface() {
        Chop chop = BeanUtils.instantiateClass(Chop.class);
    }

    @Test
    public void testInitializeUsingConstructor() {
        try {
            Constructor<Angel> constructor = Angel.class.getConstructor(String.class);
            Angel angle = BeanUtils.instantiateClass(constructor, "Marglet");
            assertThat(angle.getAlias()).isEqualTo("Marglet");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取protected类型方法
     */
    @Test
    public void testFindMethod() {
        Angel angel = new Angel();
        Method flyMethod = BeanUtils.findMethod(Angel.class, "fly");
        try {
            flyMethod.invoke(angel, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试private方法
     */
    @Test
    public void testFindPrivateMethod(){
        Angel angel = new Angel();
        expectFailure.whenTesting()
                .that(BeanUtils.findMethod(Angel.class, "say", String.class))
                .isInstanceOf(IllegalAccessException.class);
        assertThat(expectFailure.getFailure()).hasMessageThat().contains("private");
    }
}
