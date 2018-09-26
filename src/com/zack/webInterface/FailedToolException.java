package com.zack.webInterface;

/**
 * Exception used to report what problems the WebTool runs into and relays that
 * to the user.
 *
 * @author Zack Dreitzler
 * @version 1
 */
public class FailedToolException extends Exception {

    public FailedToolException() {
        super();
    }

    public FailedToolException(String message) {
        super(message);
    }
}
