## What is Easy States?

Easy States is an event-driven [Deterministic Finite Automaton](http://en.wikipedia.org/wiki/Deterministic_finite_state_machine) implementation in Java.

It is inspired by [Erlang](http://www.erlang.org/documentation/doc-5.9.3/doc/design_principles/fsm.html) design principles which describe a Finite State Machine as a set of relations of the form:

`State(S) x Event(E) -> Actions(A), State(S')`

These relations are interpreted like follows:

If we are in state `S` and the event `E` occurs, we should perform action(s) `A` and make a transition to the state `S'`.

## How does it work?

Easy States provides APIs for key concepts of state machines:

* `State`: a particular state the machine can be on at given point in time
* `Event`: represents an event that may trigger an action and change the state of the machine
* `Transition`: represents a transition between two states of the machine when an event occurs
* `FinateStateMachine`: core abstraction of finite state machine

Using Easy States, you can define FSM transitions with an intuitive fluent API:

```java
Transition t = new TransitionBuilder()
        .sourceState(s0) //if we are in state s0
        .eventType(Event.class) // and the event E occurs
        .eventHandler(myActionA) // we should perform the actions A
        .targetState(s1) // and make a transition to the state s1
        .build();
```
## Two minutes tutorial

This tutorial is an implementation of the turnstile example described in <a href="http://en.wikipedia.org/wiki/Finite-state_machine">wikipedia</a>:

A turnstile has two states: `Locked` and `Unlocked`. There are two inputs that affect its state: putting a coin in the slot (coin) and pushing the arm (push).
In the locked state, pushing on the arm has no effect; no matter how many times the input push is given it stays in the locked state.
Putting a coin in, that is giving the machine a coin input, shifts the state from Locked to Unlocked.
In the unlocked state, putting additional coins in has no effect; that is, giving additional coin inputs does not change the state.

![turnsitle](https://raw.githubusercontent.com/j-easy/easy-states/master/easy-states-tutorials/src/main/resources/turnstile.png)

For this example, we define:

* two events: `PushEvent` and `CoinEvent`
* two actions: `Lock` and `Unlock`
* four transitions: `pushLocked`, `unlock`, `coinUnlocked` and `lock`

The complete code of the example is the following:

```java
public class Launcher {

    public static void main(String[] args) throws FiniteStateMachineException {

        /*
         * Define FSM states
         */
        State locked = new State("locked");
        State unlocked = new State("unlocked");

        Set<State> states = new HashSet<State>();
        states.add(locked);
        states.add(unlocked);

        /*
         * Define FSM transitions
         */
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

        /*
         * Build FSM instance
         */
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, locked)
                .named("Turnstile state machine")
                .registerTransition(lock)
                .registerTransition(pushLocked)
                .registerTransition(unlock)
                .registerTransition(coinUnlocked)
                .activateJmxMonitoring()
                .build();

        /*
         * Fire some events and print FSM state
         */
        System.out.println("Turnstile initial state : " + finiteStateMachine.getCurrentState().getName());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Which event do you want to fire?");
        System.out.println("1. Push [p]");
        System.out.println("2. Coin [c]");
        System.out.println("Press [q] to quit tutorial.");
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("p")) {
                System.out.println("input = " + input.trim());
                System.out.println("Firing push event..");
                finiteStateMachine.fire(new PushEvent());
                System.out.println("Turnstile state : " + finiteStateMachine.getCurrentState().getName());
            }
            if (input.trim().equalsIgnoreCase("c")) {
                System.out.println("input = " + input.trim());
                System.out.println("Firing coin event..");
                finiteStateMachine.fire(new CoinEvent());
                System.out.println("Turnstile state : " + finiteStateMachine.getCurrentState().getName());
            }
            if (input.trim().equalsIgnoreCase("q")) {
                System.out.println("input = " + input.trim());
                System.out.println("Bye!");
                System.exit(0);
            }

        }

    }
}
```

To run the tutorial, please follow these instructions:

```bash
$>git clone https://github.com/j-easy/easy-states.git
$>cd easy-states
$>mvn install
$>cd easy-states-tutorials
$>mvn exec:java -P runTurnstileTutorial
```

You will be able to fire events interactively from the console and check the evolution of the machine's state.

## License

Easy States is released under the [MIT License](http://opensource.org/licenses/mit-license.php/).

```
The MIT License

   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

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
