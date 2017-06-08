package org.jeasy.states.core;

import org.jeasy.states.api.Event;
import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TransitionDefinitionValidatorTest {

    private TransitionDefinitionValidator transitionDefinitionValidator = new TransitionDefinitionValidator();

    @Test(expected = IllegalArgumentException.class)
    public void sourceStateMustBeDefined() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalState(s2)
                .build();

        Transition transition = new TransitionBuilder()
                .targetState(s2)
                .eventType(TransitionDefinitionValidatorTest.DummyEvent.class)
                .build();

        // when
        transitionDefinitionValidator.validateTransitionDefinition(transition, finiteStateMachine);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void sourceStateMustBeDeclaredInFSMStates() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalState(s2)
                .build();

        Transition transition = new TransitionBuilder()
                .sourceState(s3)
                .targetState(s2)
                .eventType(TransitionDefinitionValidatorTest.DummyEvent.class)
                .build();

        // when
        transitionDefinitionValidator.validateTransitionDefinition(transition, finiteStateMachine);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void targetStateMustBeDefined() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalState(s2)
                .build();

        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .eventType(TransitionDefinitionValidatorTest.DummyEvent.class)
                .build();

        // when
        transitionDefinitionValidator.validateTransitionDefinition(transition, finiteStateMachine);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void targetStateMustBeDeclaredInFSMStates() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineBuilder(states, s1)
                .registerFinalState(s2)
                .build();

        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s3)
                .eventType(TransitionDefinitionValidatorTest.DummyEvent.class)
                .build();

        // when
        transitionDefinitionValidator.validateTransitionDefinition(transition, finiteStateMachine);

        // then
        // expected exception
    }

    private class DummyEvent extends Event { }

}