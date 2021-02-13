package com.example.kervel.modele;

public class EventResponse {

    private boolean error;
    private String message;
    private EventModel event;

    public EventResponse(boolean error, String message, EventModel event) {
        this.error = error;
        this.message = message;
        this.event = event;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public EventModel getEvent() {
        return event;
    }
}
