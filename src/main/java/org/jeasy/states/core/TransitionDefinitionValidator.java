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

        if (sourceState == null) {
            throw new IllegalArgumentException("No source state is defined for transition '" + transitionName + "'");
        }
        if (targetState == null) {
            throw new IllegalArgumentException("No target state is defined for transition '" + transitionName + "'");
        }
        if (transition.getEventType() == null) {
            throw new IllegalArgumentException("No event type is defined for transition '" + transitionName + "'");
        }
        if (!finiteStateMachine.getStates().contains(sourceState)) {
            throw new IllegalArgumentException("Source state '" + sourceState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
        }
        if (!finiteStateMachine.getStates().contains(targetState)) {
            throw new IllegalArgumentException("target state '" + targetState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
        }
    }

}
