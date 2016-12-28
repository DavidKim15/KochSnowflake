/*
 * KochOverflowException.java
 *
 * We pledge our honor that we have abided by the Stevens Honor System
 * Mark Del Rosario, David Kim
 *
 */
package homework4;

/**
 * 
 * Thrown when KochPanel is trying to add more lines than the set capacity
 * 
 * @version 1.0
 * @since 20161201
 * @author Mark Del Rosario, David Kim
 *
 */
public class KochOverflowException extends Exception {
	public KochOverflowException() {
		super();
	}
	
	public KochOverflowException(String message) {
		super(message);
	}
}
