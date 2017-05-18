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

import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.util.Utils;

/**
 * FSM JMX MBean Monitor implementation.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FiniteStateMachineMonitor implements FiniteStateMachineMonitorMBean {

    /**
     * The final state machine to monitor.
     */
    private FiniteStateMachine finiteStateMachine;

    /**
     * Public constructor.
     * @param finiteStateMachine the FSM instance.
     */
    public FiniteStateMachineMonitor(final FiniteStateMachine finiteStateMachine) {
        this.finiteStateMachine = finiteStateMachine;
    }

    @Override
    public String getName() {
        return finiteStateMachine.getName();
    }

    @Override
    public String getStates() {
        return Utils.dumpFSMStates(finiteStateMachine.getStates());
    }

    @Override
    public String getInitialState() {
        return finiteStateMachine.getInitialState().getName();
    }

    @Override
    public String getFinalStates() {
        return Utils.dumpFSMStates(finiteStateMachine.getFinalStates());
    }

    @Override
    public String getCurrentState() {
        return finiteStateMachine.getCurrentState().getName();
    }

    @Override
    public String getLastEvent() {
        return finiteStateMachine.getLastEvent().toString();
    }


    @Override
    public String getLastTransition() {
        return finiteStateMachine.getLastTransition().toString();
    }

}
