package near2u.com.near2u.entities;

import java.util.Date;

/**
 * Class that represents the details of a user.
 */
public class UserDetails {

    Integer userId;
    String username;
    String occupation;
    String birthdate;
    String hobbies;
    String interests;

    public UserDetails(Integer userId, String username, String occupation, String birthdate, String hobbies, String interests) {
        this.userId = userId;
        this.username = username;
        this.occupation = occupation;
        this.birthdate = birthdate;
        this.hobbies = hobbies;
        this.interests = interests;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
