package cn.crabime;

/**
 * Created by crabime on 6/20/17.
 * 使用spring容器去创建单类例
 */
public class SingletonBean {
    private String name;
    public static SingletonBean singletonBean;

    private SingletonBean(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SingletonBean newInstance(){
        if(singletonBean == null){
            singletonBean = new SingletonBean();
        }
        return singletonBean;
    }

    @Override
    public String toString() {
        return "当前单类例的name属性值为:" + this.name;
    }
}
