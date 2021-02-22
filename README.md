# FP_Design
Considered an example(a digital circuit simulator) of how assignments(they're represents the stateful objects) and higher-order functions can be combined in 
interesting ways. This example also shows how to build programs that do discrete event simulation.
# Digital Circuits
  1. A digital circuit is composed of wires and of functional components.
  2. Wires transport signals that are transformed by components.
  3. Signals are represented using booleans true and false.
  
  The base components (gates) are:
    1. The Inverter, whose output is the inverse of its input.
    2. The AND Gate, whose output is the conjunction of its inputs.
    3. The OR Gate, whose output is the disjunction of its inputs.
    
  The components have a reaction time (or delay), i.e. their outputs don’t change immediately after a change to their inputs.
  
# Implementation
  The class 'Wire' and the functions 'inverter', 'andGate', and 'orGate' represent a small description language of digital circuits.
  These implementations are based on a simple API for discrete event simulation.
  ## Actions
    A discrete event simulator performs actions, specified by the user at a given moment.
    An action is a function that doesn’t take any parameters and which returns Unit: type Action = () => Unit.
    The time is simulated; it has nothing to with the actual time.
  
