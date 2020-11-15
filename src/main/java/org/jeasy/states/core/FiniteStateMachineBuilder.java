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

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * FSM builder : this class is the main entry point to build FSM instances.
 * Only states set and initial state are mandatory.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FiniteStateMachineBuilder {

    private static final Logger LOGGER = Logger.getLogger(FiniteStateMachineBuilder.class.getName());

    static {
        try {
            if (System.getProperty("java.util.logging.config.file") == null &&
                    System.getProperty("java.util.logging.config.class") == null) {
                LogManager.getLogManager().readConfiguration(FiniteStateMachineBuilder.class.getResourceAsStream("/logging.properties"));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to load log configuration file", e);
        }
    }

    private final FiniteStateMachineImpl finiteStateMachine;
    private final FiniteStateMachineDefinitionValidator finiteStateMachineDefinitionValidator;
    private final TransitionDefinitionValidator transitionDefinitionValidator;

    /**
     * Create a new {@link FiniteStateMachineBuilder}.
     *
     * @param states set of the machine
     * @param initialState of the machine
     */
    public FiniteStateMachineBuilder(final Set<State> states, final State initialState) {
        finiteStateMachine = new FiniteStateMachineImpl(states, initialState);
        finiteStateMachineDefinitionValidator = new FiniteStateMachineDefinitionValidator();
        transitionDefinitionValidator = new TransitionDefinitionValidator();
    }

    /**
     * Register a transition within FSM transitions set.
     * If the transition is not valid, this method may throw an {@link IllegalArgumentException}.
     * @param transition the transition to register
     * @return a configured FSM Builder instance
     */
    public FiniteStateMachineBuilder registerTransition(final Transition transition) {
        transitionDefinitionValidator.validateTransitionDefinition(transition, finiteStateMachine);
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
     * Build a FSM instance. This method checks if FSM definition is valid.
     * If FSM state is not valid, this methods throws an {@link IllegalStateException}
     * @return a configured FSM instance
     */
    public FiniteStateMachine build() {
        finiteStateMachineDefinitionValidator.validateFiniteStateMachineDefinition(finiteStateMachine);
        return finiteStateMachine;
    }

}
