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
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TransitionImplTest {

    private State s1 = new State("s1");
    private State s2 = new State("s2");

    @Test
    public void whenTwoTransitionsHaveTheSameSourceStateAndTheSameTriggeringEvent_thenTheyShouldBeEqual() {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isTrue();
    }

    @Test
    public void whenTwoTransitionsHaveTheSameSourceStateButDifferentTriggeringEvent_thenTheyShouldNotBeEqual() {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s1).eventType(AnotherDummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    @Test
    public void whenTwoTransitionsHaveTheSameTriggeringEventButDifferentSourceState_thenTheyShouldNotBeEqual() {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s2).eventType(DummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    @Test
    public void whenTwoTransitionsHaveDifferentTriggeringEventsAndDifferentSourceStates_thenTheyShouldNotBeEqual() {
        // Given
        Transition t1 = new TransitionBuilder().sourceState(s1).eventType(DummyEvent.class).build();
        Transition t2 = new TransitionBuilder().sourceState(s2).eventType(AnotherDummyEvent.class).build();

        // When
        boolean equals = t1.equals(t2);

        // Then
        Assertions.assertThat(equals).isFalse();
    }

    private static class DummyEvent extends AbstractEvent { }
    private static class AnotherDummyEvent extends AbstractEvent { }

}
