package project.teaming.alarm.exception;

public class AlarmNotFoundException extends RuntimeException {
    public AlarmNotFoundException(String message) {
        super(message);
    }
}
