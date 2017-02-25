package com.github.rskupnik.jqnfsm.implementation;

/**
 * An abstract to represent a State that the FSM is currently in.
 * A State has to provide logic to be executed in the update() method
 * and human-readable identifier in the getId() method.
 */
public abstract class State {

    private FiniteStateMachine fsm;

    public State(FiniteStateMachine fsm) {
        this.fsm = fsm;
    }

    public abstract String getId();
    public abstract void update();

    protected FiniteStateMachine getFsm() {
        return fsm;
    }
}
