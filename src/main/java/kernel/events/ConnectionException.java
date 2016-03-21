package kernel.events;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public class ConnectionException extends Exception {

    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }
}
