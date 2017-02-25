package com.github.rskupnik.jqnfsm.implementation;

import java.util.HashMap;
import java.util.Map;

/**
 * Coordinator is the main interface for this system.
 * It's responsible for inter-node communication, passing messages
 * and creating and storing Nodes.
 *
 * @author rskupnik
 */
public final class Coordinator {

    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private Node mainThreadNode;    // One Node can be run on the main thread - useful for OpenGL

    /**
     * Creates a new Node containing the provided FiniteStateMachine
     * and associated with the provided id.
     *
     * @param id identifier of the node used to interact with it
     * @param fsm the FiniteStateMachine implementation to be ran on the Node
     * @throws IllegalArgumentException if an FSM with this id already exists
     */
    public void createNode(final int id, final FiniteStateMachine fsm) {
        if (nodes.containsKey(id))
            throw new IllegalArgumentException("An FSM with id "+id+" already exists");

        nodes.put(id, new Node(this, id, fsm, false));
    }

    /**
     * Creates a new Node that will run on the main thread. In order to do that,
     * you need to periodically call tickMainThreadNode(), preferably from your
     * update() or render() loop.
     *
     * @param id identifier of the node used to interact with it
     * @param fsm the FiniteStateMachine implememtation to be ran on the Node
     * @throws IllegalArgumentException if an FSM with this id already exists or there already
     * is a Node running on the main thread
     */
    public void createNodeOnMainThread(final int id, final FiniteStateMachine fsm) {
        if (nodes.containsKey(id))
            throw new IllegalArgumentException("An FSM with id "+id+" already exists");

        if (mainThreadNode != null)
            throw new IllegalArgumentException("A node running on the main thread already exists");

        mainThreadNode = new Node(this, id, fsm, true);
        nodes.put(id, mainThreadNode);
    }

    /**
     * Ticks the Node running on the main thread if one exists.
     * The Node will pop a message from its queue and act upon it.
     * Should be called on each update() or render() loop.
     */
    public void tickMainThreadNode() {
        if (mainThreadNode != null)
            mainThreadNode.tick();
    }

    /**
     * Sends a Message to a node with the specified id.
     * If that node does not exists, does nothing.
     *
     * @param msg Message to be sent
     * @param nodeId identifier of the Node to send the Message to
     */
    public void sendMessage(final Message msg, final int nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null)
            return;

        node.receiveMessage(msg);
    }

    /**
     * Iteratively calls destroy() on each of the Nodes
     */
    public void destroy() {
        for (Node node : nodes.values()) {
            node.destroy();
        }

        if (mainThreadNode != null)
            mainThreadNode.destroy();
    }
}
