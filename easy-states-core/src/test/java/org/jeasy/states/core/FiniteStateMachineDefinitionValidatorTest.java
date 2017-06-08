package org.jeasy.states.core;

import org.jeasy.states.api.Event;
import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FiniteStateMachineDefinitionValidatorTest {

    private FiniteStateMachineDefinitionValidator validator = new FiniteStateMachineDefinitionValidator();

    @Test(expected = IllegalStateException.class)
    public void whenInitialStateDoesNotBelongToMachineStates_thenShouldThrowIllegalStateException() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineImpl(states, s3);

        // when
        validator.validateFiniteStateMachineDefinition(finiteStateMachine);

        // then
        // expected exception
    }

    @Test(expected = IllegalStateException.class)
    public void whenFinalStateDoesNotBelongToMachineStates_thenShouldThrowIllegalStateException() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineImpl(states, s1);
        finiteStateMachine.registerFinalState(s3);

        // when
        validator.validateFiniteStateMachineDefinition(finiteStateMachine);

        // then
        // expected exception
    }

    @Test
    public void whenRegisterTwoTransitionsWithSameSourceStateAndEventType_thenOnlyTheLatestOneShouldBeRegistered() throws Exception {
        // given
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        Set<State> states = new HashSet<>(Arrays.asList(s1, s2, s3));
        FiniteStateMachine finiteStateMachine = new FiniteStateMachineImpl(states, s1);
        finiteStateMachine.registerFinalState(s2);

        Transition t1 = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(DummyEvent.class)
                .build();
        Transition t2 = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s3)
                .eventType(DummyEvent.class)
                .build();

        // when
        finiteStateMachine.registerTransition(t1);
        finiteStateMachine.registerTransition(t2);

        // then
        assertThat(finiteStateMachine.getTransitions()).containsOnly(t2); // transitions are unique according to source state and event type
    }

    private class DummyEvent extends Event { }

}