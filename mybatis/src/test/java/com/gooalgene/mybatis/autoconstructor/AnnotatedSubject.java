package com.gooalgene.mybatis.autoconstructor;

import org.apache.ibatis.annotations.AutomapConstructor;

/**
 * Created by crabime on 11/13/17.
 */
public class AnnotatedSubject {
    private final int id;
    private final String name;
    private final int age;
    private final int height;
    private final int weight;

    public AnnotatedSubject(final int id, final String name, final int age, final int height, final int weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @AutomapConstructor
    public AnnotatedSubject(final int id, final String name, final int age, final Integer height, final Integer weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height == null ? 0 : height;
        this.weight = weight == null ? 0 : weight;
    }
}
