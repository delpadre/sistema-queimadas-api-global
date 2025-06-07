package com.sistemadequeimadas.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
    private Map<String, String> details;

    public ErrorMessage() {
    }

    public ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String description, Map<String, String> details) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
        this.details = details;
    }

    public ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String description) {
        this(statusCode, timestamp, message, description, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
