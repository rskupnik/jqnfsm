package com.github.rskupnik.jqnfsm.implementation;

public abstract class FiniteStateMachine {

    private State state;

    protected void update(Message msg) {
        if (msg != null)
            parseInput(msg);

        if (state != null)
            state.update();
    }

    protected abstract void parseInput(Message msg);

    protected State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
