package com.github.rskupnik.jqnfsm.implementation;

/**
 * An abstract for implementing various Messages to be used
 * as a means of communication between Nodes/FSMs.
 */
public abstract class Message {

    private final int id;

    public Message(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
