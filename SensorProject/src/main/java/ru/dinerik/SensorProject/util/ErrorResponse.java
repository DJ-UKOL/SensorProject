package ru.dinerik.SensorProject.util;

public class ErrorResponse {

    private String message;     // сообщение об ошибке
    private long timestamp;     // в мс отметка времени

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
