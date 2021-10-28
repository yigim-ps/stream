package stream.sdk;

public class Error extends Exception {

    public Error(String code, String message) {
        super(String.format("%s, %s", code, message));
    }
}