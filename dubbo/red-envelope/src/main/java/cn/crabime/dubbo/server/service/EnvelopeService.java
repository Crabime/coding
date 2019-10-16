package cn.crabime.dubbo.server.service;

public interface EnvelopeService {

    /**
     * 发红包
     * @param username 用户名
     * @param envelopeNum 红包个数
     * @param sum 发送金额
     */
    public void sendEnvelope(String username, int sum, int envelopeNum);
}
