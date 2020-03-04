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

import org.jeasy.states.api.Event;
import org.jeasy.states.api.EventHandler;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.jeasy.states.util.Utils;

final class TransitionImpl<E extends Event> implements Transition {

    private String name;
    private State sourceState;
    private State targetState;
    private Class<E> eventType;
    private EventHandler<E> eventHandler;

    public TransitionImpl() {
        name = Utils.DEFAULT_TRANSITION_NAME;
    }

    public State getSourceState() {
        return sourceState;
    }

    public void setSourceState(State sourceState) {
        this.sourceState = sourceState;
    }

    public State getTargetState() {
        return targetState;
    }

    public void setTargetState(State targetState) {
        this.targetState = targetState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<E> getEventType() {
        return eventType;
    }

    public void setEventType(Class<E> eventType) {
        this.eventType = eventType;
    }

    public EventHandler<? extends Event> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler<E> eventHandler) {
        this.eventHandler = eventHandler;
    }

    /*
     * Transitions are unique according to source state and triggering event type
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionImpl that = (TransitionImpl) o;

        return eventType.equals(that.eventType) && sourceState.equals(that.sourceState);

    }

    @Override
    public int hashCode() {
        int result = sourceState.hashCode();
        result = 31 * result + eventType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Transition");
        sb.append("{name='").append(name).append('\'');
        sb.append(", sourceState=").append(sourceState.getName());
        sb.append(", targetState=").append(targetState.getName());
        sb.append(", eventType=").append(eventType);
        if (eventHandler != null) {
            sb.append(", eventHandler=").append(eventHandler.getClass().getName());
        }
        sb.append('}');
        return sb.toString();
    }
}
