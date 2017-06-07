package org.jeasy.states.core;

import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;

class TransitionDefinitionValidator {

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
    void validateTransitionDefinition(final Transition transition, FiniteStateMachine finiteStateMachine) {

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
