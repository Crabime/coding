package cn.crabime.practice.mybatis;

public enum Grade implements EnumCode {

    FATHER(10), MOTHER(20), CHILD(30);

    private int code;

    Grade(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
