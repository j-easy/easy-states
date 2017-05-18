package org.jeasy.states.core;

import org.jeasy.states.api.Event;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TransitionImplTest {

    private State s1 = new State("s1");
    private State s2 = new State("s");

    @Test
    public void whenTwoTransitionsHaveTheSameSourceStateAndTheSameTriggeringEvent_thenTheyShouldBeEqual() throws Exception {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isTrue();
    }

    @Test
    public void whenTwoTransitionsHaveTheSameSourceStateButDifferentTriggeringEvent_thenTheyShouldNotBeEqual() throws Exception {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s1).eventType(AnotherDummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    @Test
    public void whenTwoTransitionsHaveTheSameTriggeringEventButDifferentSourceState_thenTheyShouldNotBeEqual() throws Exception {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s2).eventType(DummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    @Test
    public void whenTwoTransitionsHaveDifferentTriggeringEventsAndDifferentSourceStates_thenTheyShouldNotBeEqual() throws Exception {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s2).eventType(AnotherDummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    private class DummyEvent extends Event { }
    private class AnotherDummyEvent extends Event { }

}