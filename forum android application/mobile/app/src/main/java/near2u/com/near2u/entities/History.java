package near2u.com.near2u.entities;

import java.util.Date;


public class History {

    int id;
    String message;
    String recepientName;
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

    public String getRecepientName() {
        return recepientName;
    }

    public void setRecepientName(String recepientName) {
        this.recepientName = recepientName;
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
        return senderName + " sent to " + recepientName + "\n" + message + "\n" + " on " + date;
    }
}
