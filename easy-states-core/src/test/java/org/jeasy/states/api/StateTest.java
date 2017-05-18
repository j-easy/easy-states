package org.jeasy.states.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StateTest {

    @Test
    public void whenTwoStatesHaveTheSameName_thenTheyShouldBeEqual() throws Exception {
        // Given
        State s1 = new State("s1");
        State s2 = new State("s1");

        // When
        boolean equals = s1.equals(s2);

        // Then
        Assertions.assertThat(equals).isTrue();
    }

    @Test
    public void whenTwoStatesHaveDifferentNames_thenTheyShouldNotBeEqual() throws Exception {
        // Given
        State s1 = new State("s1");
        State s2 = new State("s2");

        // When
        boolean equals = s1.equals(s2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }
}