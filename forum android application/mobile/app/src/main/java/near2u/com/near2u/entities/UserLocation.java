package near2u.com.near2u.entities;


public class UserLocation {

    Integer userId;
    String username;
    Double longitude;
    Double latitude;

    public UserLocation(Integer userId, String username, Double longitude, Double latitude) {
        this.userId = userId;
        this.username = username;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
