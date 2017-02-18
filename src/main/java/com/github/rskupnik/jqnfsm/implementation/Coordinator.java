package com.github.rskupnik.jqnfsm.implementation;

import java.util.HashMap;
import java.util.Map;

public final class Coordinator {

    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private Node mainThreadNode;

    public void createNode(final int id, final FiniteStateMachine fsm) {
        if (nodes.containsKey(id))
            throw new IllegalArgumentException("An FSM with id "+id+" already exists");

        nodes.put(id, new Node(this, id, fsm, false));
    }

    public void createNodeOnMainThread(final int id, final FiniteStateMachine fsm) {
        if (nodes.containsKey(id))
            throw new IllegalArgumentException("An FSM with id "+id+" already exists");

        if (mainThreadNode != null)
            throw new IllegalArgumentException("A node running on the main thread already exists");

        mainThreadNode = new Node(this, id, fsm, true);
        nodes.put(id, mainThreadNode);
    }

    public void tickMainThreadNode() {
        if (mainThreadNode != null)
            mainThreadNode.tick();
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
