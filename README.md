# jQnFSM
Queues and Finite State Machines architecture implementation for Java, as described in the [book by ITHare](http://ithare.com/book-beta-testing-development-and-deployment-of-massively-multiplayer-games-from-social-games-to-mmofps-with-stock-exchanges-in-between/).

## Architecture

// TODO: Add a diagram here

QnFSM is based on two things: Queues and Finite State Machines.
FiniteStateMachines should be deterministric and contain the actual logic to be executed.
Queues are used to pass input to the FSMs and allow for communication.
A Node is a wrapper object that contains a single FSM, a Queue that holds input for that FSM
and a Thread that will periodically pop a single input object from the Queue and pass it to the FSM for handling.
It is possible to create a "dead" Node with no Thread to run it - useful for when we want to run a Node
on the main thread, for example due to OpenGL context being bound to it.

## How to use it

An example is provided on my github, named GDX-QnFSM.

// TODO: Add a link here
