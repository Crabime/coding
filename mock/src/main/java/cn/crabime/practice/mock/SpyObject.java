package cn.crabime.practice.mock;

/**
 * 使用spy去代理一个对象
 */
public class SpyObject {

    private SpyCalcProcedure procedure = new SpyCalcProcedure();

    /**
     * 模拟外部不可用方法
     */
    public void methodA() {
        throw new RuntimeException("此路不通");
    }

    public void methodB() {
        System.out.println("我是方法B");
    }

    public void methodC() {
        System.out.println("我是methodC");
        methodA();
        methodB();
    }

    /**
     * 模拟该方法需调用外部耗时方法
     * @param len 字符串生成长度
     */
    public void testCallTimeConsumedMethod(int len) {
        methodB();
        String result = procedure.calcProcess(len);
        System.out.println("执行结果为" + result);
    }

    public void setProcedure(SpyCalcProcedure procedure) {
        this.procedure = procedure;
    }
}
