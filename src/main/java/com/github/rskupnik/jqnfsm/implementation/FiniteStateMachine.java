package com.github.rskupnik.jqnfsm.implementation;

/**
 * An abstract to use when implementing FiniteStateMachines to be ran on Nodes.
 * FiniteStateMachines should be deterministic.
 * FiniteStateMachines can receive Messages as input and change States.
 */
public abstract class FiniteStateMachine {

    private State state;    // The current state of the FSM

    /**
     * The "tick" method. If the msg is not null, it is parsed and then
     * the current state's update() method is called
     *
     * @param msg a Message input
     */
    protected void update(Message msg) {
        if (msg != null)
            parseInput(msg);

        if (state != null)
            state.update();
    }

    /**
     * Parses a Message input
     */
    protected abstract void parseInput(Message msg);

    protected State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
