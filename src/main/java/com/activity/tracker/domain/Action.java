package com.activity.tracker.domain;

public enum Action {

    JUMP("jump"),
    RUN("run"),
    HOP("hop");

    private final String name;

    private Action(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
