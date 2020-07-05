package cn.crabime.practice.mock;

public class InOrderMockClass {

    private InOrderFirst inOrderFirst;

    private InOrderSecond inOrderSecond;

    public void main() {
        System.out.println("主方法调用");
        inOrderFirst.calledFirst();
        inOrderSecond.callSecond();
    }

    public void callInOrder(boolean sequence) {
        System.out.println("是否顺序调用:" + sequence);
        if (sequence) {
            inOrderFirst.calledFirst();
            inOrderSecond.callSecond();
        } else {
            inOrderSecond.callSecond();
            inOrderFirst.calledFirst();
        }
    }

    public void setInOrderFirst(InOrderFirst inOrderFirst) {
        this.inOrderFirst = inOrderFirst;
    }

    public void setInOrderSecond(InOrderSecond inOrderSecond) {
        this.inOrderSecond = inOrderSecond;
    }
}
