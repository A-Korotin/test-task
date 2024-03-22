package org.ra.atomidtesttask.application.user.exception;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
