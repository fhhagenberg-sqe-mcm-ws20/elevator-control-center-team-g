package at.fhhagenberg.sqe.util;

/**
 * <p>ClockTickChangeException class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class ClockTickChangeException extends Exception {

    /**
     * <p>Constructor for ClockTickChangeException.</p>
     *
     * @param errorMessage a {@link java.lang.String} object.
     */
    public ClockTickChangeException(String errorMessage) {
        super(errorMessage);
    }
}
