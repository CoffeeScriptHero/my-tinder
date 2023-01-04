package user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final int receiverId;
    private final int senderId;
    private final String message;
    private final LocalDateTime dateTime;

    public Message(int receiverId, int senderId, String message, LocalDateTime dateTime) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("Message{receiverId=%d, senderId=%d, message=%s, dateTime=%s}",
                receiverId, senderId, message, getDateTime());
    }
}
