package nl.oose.blackpool.Exceptions;

public class CouldNotLoadFileException extends Exception{
    public CouldNotLoadFileException (String message) {super(message);}

    public CouldNotLoadFileException () {super("Unable to load the required file(s)");}
}
