package org.jeasy.states.core;

import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.jeasy.states.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class FiniteStateMachineDefinitionValidator {

    /**
     * Deterministic FSM validation : for each state, exactly one outgoing transition for an event type must be defined.
     */
    void validateFiniteStateMachineDefinition(FiniteStateMachine finiteStateMachine) {

        Set<State> states = finiteStateMachine.getStates();

        //check if initial state belongs to FSM declared states.
        State initialState = finiteStateMachine.getInitialState();
        if (!states.contains(initialState)) {
            throw new IllegalStateException("Initial state '" + initialState.getName() + "' must belong to FSM states: " +
                    Utils.dumpFSMStates(states));
        }

        //check if registered final states belong to FSM declared states.
        for (State finalState : finiteStateMachine.getFinalStates()) {
            if (!states.contains(finalState)) {
                throw new IllegalStateException("Final state '" + finalState.getName() + "' must belong to FSM states: " +
                        Utils.dumpFSMStates(states));
            }
        }
    }
}
