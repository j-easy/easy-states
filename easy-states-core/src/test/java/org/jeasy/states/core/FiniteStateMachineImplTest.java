package org.jeasy.states.core;

import org.jeasy.states.api.Event;
import org.jeasy.states.api.EventHandler;
import org.jeasy.states.api.FiniteStateMachineException;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class FiniteStateMachineImplTest {

    private State s1, s2;
    @Mock
    private EventHandler eventHandler;
    private FiniteStateMachineImpl stateMachine;

    @Before
    public void setUp() throws Exception {
        s1 = new State("s1");
        s2 = new State("s2");
        Set<State> states = new HashSet<>();
        states.add(s1);
        states.add(s2);
        stateMachine = new FiniteStateMachineImpl("fsm", states, s1);
    }

    @Test
    public void whenEventIsFired_thenShouldTransitToTargetStateAndInvokeEventHandler() throws Exception {
        // Given
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(MoveEvent.class)
                .eventHandler(eventHandler)
                .build();
        stateMachine.registerTransition(transition);

        // When
        MoveEvent event = new MoveEvent();
        stateMachine.fire(event);

        // Then
        Assertions.assertThat(stateMachine.getCurrentState()).isEqualTo(s2);
        Mockito.verify(eventHandler).handleEvent(event);
    }

    @Test
    public void whenStateMachineIsInFinalState_thenAnyEventShouldNotChangeItsState() throws Exception {
        // Given
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(MoveEvent.class)
                .eventHandler(eventHandler)
                .build();
        stateMachine.registerFinalState(s1);
        stateMachine.registerTransition(transition);

        // When
        stateMachine.fire(new MoveEvent());

        // Then
        Assertions.assertThat(stateMachine.getCurrentState()).isEqualTo(s1);
    }

    @Test
    public void whenFiredEventIsNull_thenShouldNotChangeState() throws Exception {
        // Given
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(MoveEvent.class)
                .eventHandler(eventHandler)
                .build();
        stateMachine.registerTransition(transition);

        // When
        stateMachine.fire(null);

        // Then
        Assertions.assertThat(stateMachine.getCurrentState()).isEqualTo(s1);
    }

    @Test(expected = FiniteStateMachineException.class)
    public void whenEventHandlerThrowsException_thenShouldThrowFiniteStateMachineException() throws Exception {
        // Given
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(MoveEvent.class)
                .eventHandler(eventHandler)
                .build();
        stateMachine.registerTransition(transition);
        MoveEvent event = new MoveEvent();
        Exception exception = new Exception("Artificial exception for test");
        doThrow(exception).when(eventHandler).handleEvent(event);


        // When
        stateMachine.fire(event);

        // Then
        // expected exception
    }

    @Test
    public void whenNoRegisteredTransitionForTheFiredEvent_thenShouldNotChangeState() throws Exception {
        // Given
        Transition transition = new TransitionBuilder()
                .sourceState(s1)
                .targetState(s2)
                .eventType(MoveEvent.class)
                .eventHandler(eventHandler)
                .build();
        stateMachine.registerTransition(transition);

        // When
        stateMachine.fire(new StayEvent());

        // Then
        Assertions.assertThat(stateMachine.getCurrentState()).isEqualTo(s1);
    }

    private class MoveEvent extends Event { }

    private class StayEvent extends Event { }
}