package org.jeasy.states.api;

/**
 * Interface for FSM events.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface Event {

	/**
	 * Name of the event.
	 * 
	 * @return event name
	 */
	String getName();

	/**
	 * Timestamp of the event.
	 * 
	 * @return event timestamp
	 */
	long getTimestamp();

}
