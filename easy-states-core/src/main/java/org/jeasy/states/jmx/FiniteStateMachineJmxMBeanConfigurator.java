/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.states.jmx;

import org.jeasy.states.api.FiniteStateMachine;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class configure a JMX MBean for a FSM instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FiniteStateMachineJmxMBeanConfigurator {

    private static final Logger LOGGER = Logger.getLogger(FiniteStateMachineJmxMBeanConfigurator.class.getSimpleName());

    /**
     * Configure a JMX MBean for the FSM instance.
     * @param finiteStateMachine for which a JMX MBean should be configured
     */
    public void configureJMXMBean(FiniteStateMachine finiteStateMachine) {
        String finiteStateMachineName = finiteStateMachine.getName();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name;
        try {
            name = new ObjectName("org.jeasy.states.jmx:type=FiniteStateMachineMonitorMBean");
            if (!mbs.isRegistered(name)) {
                FiniteStateMachineMonitorMBean finiteStateMachineMonitorMBean = new FiniteStateMachineMonitor(finiteStateMachine);
                mbs.registerMBean(finiteStateMachineMonitorMBean, name);
                LOGGER.info(String.format("JMX MBean registered successfully for state machine %s as: %s", finiteStateMachineName, name.getCanonicalName()));
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Unable to register a JMX MBean for state machine %s", finiteStateMachineName), e);
        }
    }
}
