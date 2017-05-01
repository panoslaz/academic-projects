package near2u.com.near2u.entities;

import java.util.Date;

/**
 * Class that represents a message of the user
 * in the list of his history.
 */
public class History {

    int id;
    String message;
    String recipientName;
    String senderName;
    boolean isUserRecipient;
    String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean isUserRecipient() {
        return isUserRecipient;
    }

    public void setIsUserRecipient(boolean isUserRecipient) {
        this.isUserRecipient = isUserRecipient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return senderName + " sent to " + recipientName + "\n" + message + "\n" + " on " + date;
    }
}
