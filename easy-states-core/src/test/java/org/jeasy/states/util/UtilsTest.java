package org.jeasy.states.util;

import org.jeasy.states.api.State;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void dumpFSMStates() throws Exception {
        // given
        final State s1 = new State("s1");
        final State s2 = new State("s2");
        Set<State> states = new HashSet<>(asList(s1, s2));

        // when
        String fsmStates = Utils.dumpFSMStates(states);

        // then
        assertThat(fsmStates).isEqualTo("s1;s2;");
    }

}