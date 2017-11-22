package com.gooalgene.mybatis.autoconstructor;

/**
 * Created by crabime on 11/13/17.
 * 原始对象
 */
public class PrimitiveSubject {
    private final int id;
    private final String name;
    private final int age;
    private final int height;
    private final int weight;

    public PrimitiveSubject(final int id, final String name, final int age, final int height, final int weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }
}
