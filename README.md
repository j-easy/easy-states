***

<div align="center">
    <b><em>Easy States</em></b><br>
    The simple, stupid finite state machine for Java&trade;
</div>

<div align="center">

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Build Status](https://github.com/j-easy/easy-states/workflows/Java%20CI/badge.svg)](https://github.com/j-easy/easy-states/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jeasy/easy-states/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.jeasy|easy-states|2.0.0|)
[![Javadoc](https://www.javadoc.io/badge/org.jeasy/easy-states.svg)](http://www.javadoc.io/doc/org.jeasy/easy-states)

</div>

***

## Latest news

* 13/03/2020: Version 2.0.0 is now released! This version is based on Java 8 and comes with a number of enhancements. Take a look at the [release notes](https://github.com/j-easy/easy-states/releases) for all details.

## What is Easy States?

Easy States is an event-driven [Deterministic Finite Automaton](http://en.wikipedia.org/wiki/Deterministic_finite_state_machine) implementation in Java.

It is inspired by [Erlang](http://www.erlang.org/documentation/doc-5.9.3/doc/design_principles/fsm.html) design principles which describe a Finite State Machine as a set of relations of the form:

`State(S) x Event(E) -> Actions(A), State(S')`

These relations are interpreted like follows:

If we are in state `S` and the event `E` occurs, we should perform action(s) `A` and make a transition to the state `S'`.

## How does it work?

Easy States provides APIs for key concepts of state machines:

* `State`: a particular state on which the machine can be at a given point in time
* `Event`: represents an event that may trigger an action and change the state of the machine
* `Transition`: represents a transition between two states of the machine when an event occurs
* `FiniteStateMachine`: core abstraction of a finite state machine

Using Easy States, you can define FSM transitions with an intuitive fluent API:

```java
Transition t = new TransitionBuilder()
        .sourceState(s0) //if we are in state s0
        .eventType(Event.class) // and the event E occurs
        .eventHandler(myActionA) // we should perform the actions A
        .targetState(s1) // and make a transition to the state s1
        .build();
```

## How to use it?

Easy States has no dependencies. You can [download](https://repo.maven.apache.org/maven2/org/jeasy/easy-states/) the `easy-states-2.0.0.jar` file and add it to your application's classpath.
If you use maven, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.jeasy</groupId>
    <artifactId>easy-states</artifactId>
    <version>2.0.0</version>
</dependency>
```

Easy States requires a Java 8+ runtime.

## Two minutes tutorial

This tutorial is an implementation of the turnstile example described in <a href="http://en.wikipedia.org/wiki/Finite-state_machine">wikipedia</a>:

A turnstile has two states: `Locked` and `Unlocked`. There are two inputs that affect its state: putting a coin in the slot (coin) and pushing the arm (push).
In the locked state, pushing on the arm has no effect; no matter how many times the input push is given it stays in the locked state.
Putting a coin in, that is giving the machine a coin input, shifts the state from `Locked` to `Unlocked`.
In the unlocked state, putting additional coins in has no effect; that is, giving additional coin inputs does not change the state.

<div align="center">

![turnsitle](https://raw.githubusercontent.com/j-easy/easy-states/master/src/test/java/org/jeasy/states/samples/turnstile/turnstile.png)

</div>

For this example, we define:

* two states: `Locked` and `Unlocked`
* two events: `PushEvent` and `CoinEvent`
* two actions: `Lock` and `Unlock`
* and four transitions: `pushLocked`, `unlock`, `coinUnlocked` and `lock`

#### 1. First, let's define states

```java
State locked = new State("locked");
State unlocked = new State("unlocked");

Set<State> states = new HashSet<>();
states.add(locked);
states.add(unlocked);
```

#### 2. Then, define events

```java
class PushEvent extends AbstractEvent { }
class CoinEvent extends AbstractEvent { }
```

#### 3. Then transitions

```java
Transition unlock = new TransitionBuilder()
        .name("unlock")
        .sourceState(locked)
        .eventType(CoinEvent.class)
        .eventHandler(new Unlock())
        .targetState(unlocked)
        .build();

Transition pushLocked = new TransitionBuilder()
        .name("pushLocked")
        .sourceState(locked)
        .eventType(PushEvent.class)
        .targetState(locked)
        .build();

Transition lock = new TransitionBuilder()
        .name("lock")
        .sourceState(unlocked)
        .eventType(PushEvent.class)
        .eventHandler(new Lock())
        .targetState(locked)
        .build();

Transition coinUnlocked = new TransitionBuilder()
        .name("coinUnlocked")
        .sourceState(unlocked)
        .eventType(CoinEvent.class)
        .targetState(unlocked)
        .build();
```

#### 4. And finally the finite state machine

```java
FiniteStateMachine turnstileStateMachine = new FiniteStateMachineBuilder(states, locked)
        .registerTransition(lock)
        .registerTransition(pushLocked)
        .registerTransition(unlock)
        .registerTransition(coinUnlocked)
        .build();
```

The complete code of this tutorial can be found [here](https://github.com/j-easy/easy-states/tree/master/src/test/java/org/jeasy/states/samples/turnstile).

To run the tutorial, please follow these instructions:

```bash
$>git clone https://github.com/j-easy/easy-states.git
$>cd easy-states
$>mvn install
$>mvn exec:java -P runTurnstileTutorial
```

You will be able to fire events interactively from the console and check the evolution of the machine's state.

## License

Easy States is released under the terms of the MIT License:

```
The MIT License

   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in
   all copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
   THE SOFTWARE.
```
