package com.lotto.domain.resultannouncer;

public enum AnnouncementMessage {
    ID_DOES_NOT_EXIST_MESSAGE("Given ID: %s does not exist"),
    WAIT_MESSAGE("Results are being calculated, please come back later"),
    WIN_MESSAGE("Congratulations, you won!"),
    LOSE_MESSAGE("No luck, try again!"),
    ALREADY_CHECKED("You have already checked your ticket, please come back later");
    
    final String message;
    
    AnnouncementMessage(final String message) {
        this.message = message;
    }
    
    public String format(String string) {
        return String.format(this.message, string);
    }
}
