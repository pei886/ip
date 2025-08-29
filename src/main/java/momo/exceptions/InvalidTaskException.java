package momo.exceptions;

public class InvalidTaskException extends MomoException {
    public InvalidTaskException() {
        super("Please provide a valid task number!");
    }
}