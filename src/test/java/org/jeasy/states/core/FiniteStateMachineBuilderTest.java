/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.states.core;

import org.jeasy.states.api.AbstractEvent;
import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FiniteStateMachineBuilderTest {

    @Test
    public void testRegisterTransition() {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        Set<State> states = new HashSet<>();
        states.add(s1);states.add(s2);
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(FiniteStateMachineBuilderTest.DummyEvent.class)
                .build();

        // when
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerTransition(transition)
                .build();

        // then
        assertThat(finiteStateMachine.getTransitions()).containsExactly(transition);
    }

    @Test
    public void testRegisterTransitions() {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        Set<State> states = new HashSet<>();
        states.add(s1);
        states.add(s2);
        Transition t1 = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(FiniteStateMachineBuilderTest.DummyEvent.class)
                .build();
        Transition t2 = new TransitionBuilder()
                .sourceState(s2)
                .targetState(s1)
                .eventType(FiniteStateMachineBuilderTest.AnotherDummyEvent.class)
                .build();
        Set<Transition> transitions = new HashSet<>();
        transitions.add(t1);
        transitions.add(t2);

        // when
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerTransitions(transitions)
                .build();

        // then
        assertThat(finiteStateMachine.getTransitions()).contains(t1, t2);
    }

    @Test
    public void testRegisterFinalState() {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        Set<State> states = new HashSet<>();
        states.add(s1);
        states.add(s2);

        // when
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalState(s2)
                .build();

        // then
        assertThat(finiteStateMachine.getFinalStates()).contains(s2);
    }

    @Test
    public void testRegisterFinalStates() {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>();
        states.add(s1);
        states.add(s2);
        states.add(s3);
        Set<State> finalStates = new HashSet<>();
        finalStates.add(s2);
        finalStates.add(s3);

        // when
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalStates(finalStates)
                .build();

        // then
        assertThat(finiteStateMachine.getFinalStates()).contains(s2, s3);
    }

    private static class DummyEvent extends AbstractEvent { }
    private static class AnotherDummyEvent extends AbstractEvent { }

}
