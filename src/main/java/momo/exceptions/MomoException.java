package momo.exceptions;

public class MomoException extends Exception{
    public MomoException(String message){
        super("OOPS!!! " + message);
    }
}
