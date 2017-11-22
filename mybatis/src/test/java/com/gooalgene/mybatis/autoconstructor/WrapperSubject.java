package com.gooalgene.mybatis.autoconstructor;

/**
 * Created by crabime on 11/13/17.
 */
public class WrapperSubject {
    private final int id;
    private final String name;
    private final int age;
    private final int height;
    private final int weight;

    public WrapperSubject(final int id, final String name, final int age, final Integer height, final Integer weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height == null ? 0 : height;
        this.weight = weight == null ? 0 : weight;
    }
}
