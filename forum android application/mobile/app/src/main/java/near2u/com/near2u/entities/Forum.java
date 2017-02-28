package near2u.com.near2u.entities;

public class Forum {

    Integer id;
    String description;

    public Forum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
