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

An example is provided on my github: [GDX-QnFSM](https://github.com/rskupnik/gdx-qnfsm)

## License

Copyright 2017 Radosław Skupnik

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
