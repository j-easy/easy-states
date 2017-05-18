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

package org.jeasy.states.api;

/**
 * Exception class thrown if an exception occurs during event handling.
 * This class gives access to the {@link Transition} and {@link Event} related to the exception.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FiniteStateMachineException extends Exception {

    /**
     * The transition where the exception occurred.
     */
    private Transition transition;

    /**
     * The event triggered when the exception occurred.
     */
    private Event event;

    /**
     * The original cause of the exception.
     */
    private Throwable cause;

    public FiniteStateMachineException(final Transition transition, final Event event, final Throwable cause) {
        this.transition = transition;
        this.event = event;
        this.cause = cause;
    }

    public Transition getTransition() {
        return transition;
    }

    public Event getEvent() {
        return event;
    }

    public Throwable getCause() {
        return cause;
    }
}
