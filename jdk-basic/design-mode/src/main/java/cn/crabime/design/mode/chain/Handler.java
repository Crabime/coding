package cn.crabime.design.mode.chain;

abstract class Handler {

    private Handler nextHandler;

    abstract void handleRequest(String body);

    public Handler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
