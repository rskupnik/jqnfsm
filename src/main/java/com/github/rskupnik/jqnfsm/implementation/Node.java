package com.github.rskupnik.jqnfsm.implementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

final class Node {

    private final Coordinator Coordinator;
    private final int id;
    private final FiniteStateMachine fsm;
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
    private final Thread nodeThread;

    private boolean exit = false;

    Node(final Coordinator Coordinator, final int id, final FiniteStateMachine fsm, boolean noThread) {
        this.Coordinator = Coordinator;
        this.id = id;
        this.fsm = fsm;

        if (!noThread) {
            this.nodeThread = new Thread(new Worker());

            nodeThread.setDaemon(true);
            nodeThread.start();
        } else nodeThread = null;
    }

    void sendMessage(Message msg, int nodeId) {
        Coordinator.sendMessage(msg, nodeId);
    }

    void receiveMessage(Message msg) {
        queue.add(msg);
    }

    void destroy() {
        exit = true;
    }

    void tick() {
        Message msg = null;
        try {
            msg = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fsm.update(msg);
    }

    private class Worker implements Runnable {

        public void run() {
            while (!exit) {
                tick();
            }
        }
    }
}
