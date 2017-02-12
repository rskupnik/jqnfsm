package com.github.rskupnik.jqnfsm.implementation;

public abstract class Message {

    private final int id;

    public Message(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
