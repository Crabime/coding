package com.gooalgene.annotation;

/**
 * Created by crabime on 3/8/18.
 */
public class HelloMessageGenerator {

    private String message;

    public HelloMessageGenerator() {
    }

    public HelloMessageGenerator(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
