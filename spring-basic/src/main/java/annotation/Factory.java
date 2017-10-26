package annotation;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by crabime on 9/17/17.
 */
@Component
public class Factory {
    public void produceLining(){
        newLining();
    }

    /**
     * 这里spring会使用CGLIB创建一个已经在Spring container中定义的新的Lining实例
     * @return Lining代理对象
     */
    @Lookup
    public Lining newLining(){
        return null;
    }
}
