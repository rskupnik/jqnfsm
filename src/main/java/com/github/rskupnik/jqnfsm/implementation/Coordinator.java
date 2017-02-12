package com.github.rskupnik.jqnfsm.implementation;

import java.util.HashMap;
import java.util.Map;

public final class Coordinator {

    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();

    public void createNode(final int id, final FiniteStateMachine fsm) {
        if (nodes.containsKey(id))
            throw new IllegalArgumentException("An FSM with id "+id+" already exists");

        nodes.put(id, new Node(this, id, fsm));
    }

    public void sendMessage(final Message msg, final int nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null)
            return;

        node.receiveMessage(msg);
    }

    public void destroy() {
        for (Node node : nodes.values()) {
            node.destroy();
        }
    }
}
