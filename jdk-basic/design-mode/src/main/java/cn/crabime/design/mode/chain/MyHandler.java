package cn.crabime.design.mode.chain;

class MyHandler extends Handler {

    private String handlerName;

    public MyHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    void handleRequest(String body) {
        System.out.println(this.handlerName + "说：传入的body为：" + body);
        if (getNextHandler() != null) {
            getNextHandler().handleRequest(body);
        }
    }
}
