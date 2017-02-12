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

    Node(final Coordinator Coordinator, final int id, final FiniteStateMachine fsm) {
        this.Coordinator = Coordinator;
        this.id = id;
        this.fsm = fsm;
        this.nodeThread = new Thread(new Worker());

        nodeThread.setDaemon(true);
        nodeThread.start();
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

    private class Worker implements Runnable {

        public void run() {
            while (!exit) {
                Message msg = null;
                try {
                    msg = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (msg == null)
                    continue;

                fsm.input(msg);
            }
        }
    }
}
