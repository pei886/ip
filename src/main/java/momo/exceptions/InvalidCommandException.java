package momo.exceptions;

public class InvalidCommandException extends MomoException{
    public InvalidCommandException(){
        super("I don't understand your instructions!");
    }
}
