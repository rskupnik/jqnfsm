package com.github.rskupnik.jqnfsm.implementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Nodes are wrappers around a FiniteStateMachine that also provide a
 * Message queue for those FSMs and a Thread to execute the FSM.
 */
final class Node {

    private final Coordinator coordinator;
    private final int id;
    private final FiniteStateMachine fsm;   // The underlying FSM
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();    // A queue of Messages to act as input
    private final Thread nodeThread;    // The thread used to execute the underlying FSM

    private boolean exit = false;

    /**
     * Creates a new Node.
     *
     * @param coordinator a reference to the Coordinator to allow communication with other Nodes
     * @param id identifier of the Node
     * @param fsm a FiniteStateMachine implementation to be executed in this Node
     * @param noThread if true, this Node will be "dead" - no thread will be started to execute it.
     *                 Should be used to start a Node dedicated to be ran manually on the main thread.
     *                 If false, a Thread will be started that makes the Node periodically pop Messages
     *                 from the queue and pass them to the underlying FSM to handle them.
     */
    Node(final Coordinator coordinator, final int id, final FiniteStateMachine fsm, boolean noThread) {
        this.coordinator = coordinator;
        this.id = id;
        this.fsm = fsm;

        if (!noThread) {
            this.nodeThread = new Thread(new Worker());

            nodeThread.setDaemon(true);
            nodeThread.start();
        } else nodeThread = null;
    }

    /**
     * Send a Message to another Node
     *
     * @param msg Message to be sent
     * @param nodeId identifier of the Node to send the Message to
     */
    void sendMessage(Message msg, int nodeId) {
        coordinator.sendMessage(msg, nodeId);
    }

    /**
     * Used by the Coordinator to pass a Message into this Node's queue
     */
    void receiveMessage(Message msg) {
        queue.add(msg);
    }

    /**
     * Kills the Thread if one exists
     */
    void destroy() {
        exit = true;
    }

    /**
     * The main logic is contained here.
     * A Message is popped from the queue and passed to the FSM for handling.
     * The pop() is blocking so this will wait until a Message is available.
     */
    void tick() {
        Message msg = null;
        try {
            msg = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fsm.update(msg);
    }

    /**
     * A Runnable to be executed by the Thread that governs this Node.
     * Currently only calls tick() as often as possible.
     */
    private class Worker implements Runnable {

        public void run() {
            while (!exit) {
                tick();
            }
        }
    }
}
