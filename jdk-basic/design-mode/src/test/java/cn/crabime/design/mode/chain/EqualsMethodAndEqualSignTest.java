package cn.crabime.design.mode.chain;

import junit.framework.TestCase;
import org.junit.Test;

public class EqualsMethodAndEqualSignTest extends TestCase {

    @Test
    public void testSimpleEqualSign() {
        String a = "hello";
        String b = "hello";
        StubBean bean = new StubBean("张三");
        StubBean bean1 = new StubBean("张三");
        assertTrue(a == b);
        assertTrue(bean.equals(bean1));
        assertFalse(bean == bean1);
    }


    static class StubBean2{}

    static class StubBean {

        String name;

        public StubBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StubBean bean = (StubBean) o;
            return bean.getName().equals(this.name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
