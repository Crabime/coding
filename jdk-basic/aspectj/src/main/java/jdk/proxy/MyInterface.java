package jdk.proxy;

import java.util.List;

public interface MyInterface {

    /**
     * 获取name用户所玩游戏列表
     * @param name 用户名
     * @return 玩家玩过的游戏列表
     */
    List<String> gameList(String name);

    /**
     * 获取name用户的age，这里不做代理，默认一直返回0
     * @param name 用户名
     */
    Integer getAge(String name);
}
