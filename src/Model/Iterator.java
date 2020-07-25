/**
 * Interface for an iterator of a list of a certain object
 */
package Model;
public interface Iterator {
    boolean hasNext(); // method to check if end of collection has been reached
    Object next(); // method to return the next
}
