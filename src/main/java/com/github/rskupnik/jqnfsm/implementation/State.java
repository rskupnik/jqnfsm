package com.github.rskupnik.jqnfsm.implementation;

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
