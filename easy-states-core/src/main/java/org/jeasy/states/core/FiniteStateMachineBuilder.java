/*
 * The MIT License
 *
 *    Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *    THE SOFTWARE.
 */

package org.jeasy.states.core;

import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.jeasy.states.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * FSM builder : this class is the main entry point to build FSM instances.
 * Only states set and initial state are mandatory.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FiniteStateMachineBuilder {

    /**
     * The final state machine implementation.
     */
    private FiniteStateMachineImpl finiteStateMachine;

    public FiniteStateMachineBuilder(final String name, final Set<State> states, final State initialState) {
        finiteStateMachine = new FiniteStateMachineImpl(name, states, initialState);
    }

    /**
     * Register a transition within FSM transitions set.
     * If the transition is not valid, this method may throw an {@link IllegalArgumentException}.
     * @param transition the transition to register
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder registerTransition(final Transition transition) {
        validateTransitionDefinition(transition);
        finiteStateMachine.registerTransition(transition);
        return this;
    }

    /**
     * Register a set of transitions within FSM transitions set.
     * If a transition is not valid, this method throws an {@link IllegalArgumentException}.
     * @param transitions the transitions set to register
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder registerTransitions(final Set<Transition> transitions) {
        for (Transition transition : transitions) {
            registerTransition(transition);
        }
        return this;
    }

    /**
     * Register FSM final state which is not mandatory.
     * Once in final state, the FSM will ignore all incoming events.
     * @param finalState the FSM final state
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder registerFinalState(final State finalState) {
        finiteStateMachine.registerFinalState(finalState);
        return this;
    }

    /**
     * Register FSM final states set.
     * Once in final state, the FSM will ignore all incoming events.
     * @param finalStates the FSM final states to register
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder registerFinalStates(final Set<State> finalStates) {
        for (State finalState : finalStates) {
            registerFinalState(finalState);
        }
        return this;
    }

    /**
     * Activate Easy States JMX MBean to monitor FSM instance via JMX.
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder activateJmxMonitoring() {
        finiteStateMachine.configureJMXMBean();
        return this;
    }

    /**
     * Build a FSM instance. This method checks if FSM definition is valid.
     * If FSM state is not valid, this methods throws an {@link IllegalStateException}
     * @return a configured FSM instance
     */
    public FiniteStateMachine build() {
        validateFiniteStateMachineDefinition();
        return finiteStateMachine;
    }

    /**
     * Deterministic FSM validation : for each state, exactly one outgoing transition for an event type must be defined.
     */
    // TODO extract in separate class
    private void validateFiniteStateMachineDefinition() {

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

        for (State state: states) {

            List<Transition> outgoingTransitions = new ArrayList<>();
            for (Transition transition : finiteStateMachine.getTransitions()) {
                if (transition.getSourceState().equals(state)) {
                    outgoingTransitions.add(transition);
                }
            }
            if (outgoingTransitions.size() > 1) { //if more than one transition is defined for a state, event types must be distinct
                List<Class> eventTypes = new ArrayList<>();
                for (Transition outgoingTransition : outgoingTransitions) {
                    eventTypes.add(outgoingTransition.getEventType());
                }
                //event types must be distinct
                Set<Class> distinctEventTypes = new HashSet<>(eventTypes);
                if (distinctEventTypes.size() != eventTypes.size()) {
                    throw new IllegalStateException("More than one outgoing transition is defined with same event type for state " + state.getName());
                }
            }
        }
    }

    /**
     * Validate transition definition :
     * <ul>
     *     <li>Source and target states must be declared in FSM states set.</li>
     *     <li>Source and target states must be defined (not null)</li>
     *     <li>Event Type must be defined (not null)</li>
     *     <li>Event Handler is not mandatory</li>
     * </ul>
     * @param transition the transition to validate
     */
    // TODO extract in separate class
    private void validateTransitionDefinition(final Transition transition) {

        String transitionName = transition.getName();
        State sourceState = transition.getSourceState();
        State targetState = transition.getTargetState();

        if (!finiteStateMachine.getStates().contains(sourceState)) {
            throw new IllegalArgumentException("Source state " + sourceState + " not defined in FSM states set for transition " + transitionName);
        }
        if (sourceState == null) {
            throw new IllegalArgumentException("Source state not defined for transition " + transitionName);
        }
        if (!finiteStateMachine.getStates().contains(targetState)) {
            throw new IllegalArgumentException("target state " + targetState + " not defined in FSM states set for transition " + transitionName);
        }
        if (targetState == null) {
            throw new IllegalArgumentException("Target state not defined for transition " + transitionName);
        }
        if (transition.getEventType() == null) {
            throw new IllegalArgumentException("Event Type not defined for transition " + transitionName);
        }
    }

}
