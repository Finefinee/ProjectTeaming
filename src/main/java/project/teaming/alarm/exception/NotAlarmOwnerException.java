package project.teaming.alarm.exception;

public class NotAlarmOwnerException extends RuntimeException {
    public NotAlarmOwnerException(String message) {
        super(message);
    }
}
