package cn.crabime.practice;

import java.util.Date;

/**
 * Planet是一个不可变类，因为当它构造完成之后没有办法改变它的状态
 */
public final class Planet1 {
    /**
     * 声明为final的基本类型数据总是不可变的
     */
    private final double fMass;
    /**
     * 不可变的对象属性 (String对象不可变)
     */
    private final String fName;
    /**
     * 可变的对象属性. 在这种情况下, 这个可变属性只能被这个类改变。
     * (在其它情况下, 允许在原生类外部改变一个属性是很有意义的;
     * 这种情况就是当属性作为其它地方创建的一个对象引用)
     */
    private final Date fDateOfDiscovery;

    public Planet1(double aMass, String aName, Date aDateOfDiscovery) {
        fMass = aMass;
        fName = aName;
        //创建aDateOfDiscovery的一个私有拷贝
        //这是保持fDateOfDiscovery属性为private的唯一方式, 并且保护这个
        //类不受调用者对于原始aDateOfDiscovery对象所做任何改变的影响
        fDateOfDiscovery = new Date(aDateOfDiscovery.getTime());
    }

    /**
     * 返回一个基本类型值.
     * <p>
     * 调用者可以随意改变返回值,但是不会影响类内部。
     */
    public double getMass() {
        return fMass;
    }

    /**
     * 返回一个不可变对象
     * <p>
     * 调用者得到内部属性的一个直接引用. 由于String是不可变的所以没什么影响
     */
    public String getName() {
        return fName;
    }
// /**
// * 返回一个可变对象 - 不是一个好的方式.
// *
// * 调用者得到内部属性的一个直接引用. 这通常很危险,因为Date对象既可以
// * 被这个类改变也可以被它的调用者改变.即,类不再对fDate拥有绝对的控制。
// */
     public Date getDateOfDiscovery() {
     return fDateOfDiscovery;
     }

    /**
     * 返回一个可变对象 - 好的方式.
     * <p>
     * 返回属性的一个保护性拷贝.调用者可以任意改变返回的Date对象,但是不会
     * 影响类的内部.为什么? 因为它们没有fDate的一个引用. 更准确的说, 它们
     * 使用的是和fDate有着相同数据的另一个Date对象
     */
//    public Date getDateOfDiscovery() {
//        return new Date(fDateOfDiscovery.getTime());
//    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        Planet1 planet = new Planet1(1.0D, "earth", new Date());
        Date date = planet.getDateOfDiscovery();
        date.setTime(111111111L);
        System.out.println("the value of fDateOfDiscovery of internal class : " + planet.fDateOfDiscovery.getTime());
        System.out.println("the value of date after change its value : " + date.getTime());
    }
}

