package cn.crabime.dubbo.pay.service;

public interface PayService {

    /**
     * 扣款服务
     * @param username 用户名
     * @param money 扣除金额
     * @return 是否扣款成功
     */
    public boolean decAccount(String username, int money);
}
