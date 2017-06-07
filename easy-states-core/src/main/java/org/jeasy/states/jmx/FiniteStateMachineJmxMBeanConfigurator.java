package org.jeasy.states.jmx;

import org.jeasy.states.api.FiniteStateMachine;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FiniteStateMachineJmxMBeanConfigurator {

    private static final Logger LOGGER = Logger.getLogger(FiniteStateMachineJmxMBeanConfigurator.class.getSimpleName());

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
