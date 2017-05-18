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

package org.jeasy.states.jmx;

/**
 * A JMX MBean interface to monitor a FSM.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface FiniteStateMachineMonitorMBean {

    /**
     * Get the FSM name.
     * @return the FSM name.
     */
    String getName();

    /**
     * Get the FSM states.
     * @return the FSM states.
     */
    String getStates();

    /**
     * Get the FSM initial state.
     * @return the FSM initial state.
     */
    String getInitialState();

    /**
     * Get the FSM final states.
     * @return the FSM final states.
     */
    String getFinalStates();

    /**
     * Get the FSM current state.
     * @return the FSM current state.
     */
    String getCurrentState();

    /**
     * Get the FSM last triggered event.
     * @return the FSM last triggered event.
     */
    String getLastEvent();

    /**
     * Get the FSM last transition.
     * @return the FSM last transition.
     */
    String getLastTransition();

}
