package nl.oose.blackpool.Exceptions;

public class IncorrectBase64Exception extends Exception{
    public IncorrectBase64Exception () {super("The Base64 string provided does not meet the minimum standards set for this string.");}

    public IncorrectBase64Exception (String message) {super(message);}
}
