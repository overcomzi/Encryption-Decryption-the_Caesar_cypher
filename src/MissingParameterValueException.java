public class MissingParameterValueException extends Exception {
    String message;

    MissingParameterValueException(String message) {
        super(message);
    }
}
